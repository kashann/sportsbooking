package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.converters.LocationDtoToLocationConverter;
import com.licenta.sportsbooking.converters.LocationToLocationDtoConverter;
import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.SearchLocationRequest;
import com.licenta.sportsbooking.dto.SearchResultDTO;
import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.exceptions.NotFoundException;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.SportType;
import com.licenta.sportsbooking.repositories.LocationRepository;
import com.licenta.sportsbooking.repositories.TownRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationDtoToLocationConverter locationDtoToLocationConverter;
    private final LocationToLocationDtoConverter locationToLocationDtoConverter;
    private final SportServiceImpl sportService;
    private final TownRepository townRepository;

    @Override
    public LocationDTO findById(Long id) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        if (locationOptional.isEmpty()) {
            throw new NotFoundException("Location Not Found! For ID value: " + id);
        }
        return locationToLocationDtoConverter.convert(locationOptional.get());
    }

    @Override
    public List<LocationDTO> getLocations() {
        log.debug("I'm getting all locations");

        return locationRepository.findAll()
                .stream()
                .map(locationToLocationDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LocationDTO saveLocation(LocationDTO locationDTO, Long townId) {
        Location detachedLocation = locationDtoToLocationConverter.convert(locationDTO);
        detachedLocation.setTown(townRepository.getOne(townId));
        Location savedLocation = locationRepository.save(detachedLocation);
        log.debug("Saved Location Id: " + savedLocation.getId());
        return locationToLocationDtoConverter.convert(savedLocation);
    }

    @Override
    @Transactional
    public LocationDTO modifyLocation(LocationDTO locationDTO, Long townId) {
        LocationDTO existingLocation = findById(locationDTO.getId());
        if (existingLocation != null) {
            existingLocation.setName(locationDTO.getName());
            existingLocation.setTown(locationDTO.getTown());
            existingLocation.setSports(locationDTO.getSports());
            return saveLocation(existingLocation, townId);
        }
        else {
            return saveLocation(locationDTO, townId);
        }
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting Location Id: " + id);
        locationRepository.deleteById(id);
    }

    public List<SearchResultDTO> searchLocations(SearchLocationRequest searchLocation, boolean includeSportLessOnes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate from = null;
        LocalDate to   = null;
        if (searchLocation.getFromDate() != null && !searchLocation.getFromDate().isEmpty()) {
            from = LocalDate.parse(searchLocation.getFromDate(), formatter);
        }
        if (searchLocation.getToDate() != null && !searchLocation.getToDate().isEmpty()) {
            to = LocalDate.parse(searchLocation.getToDate(), formatter);
        }

        return searchLocations(searchLocation.getSports(), from, to, includeSportLessOnes);
    }

    public List<SearchResultDTO> searchLocations(List<String> sports, LocalDate from, LocalDate to, boolean includeSportLessOnes) {
        return searchLocations(sports, from, to, null, includeSportLessOnes);
    }

    public List<SearchResultDTO> searchLocations(List<String> sports, LocalDate from, LocalDate to, String sort) {
        return searchLocations(sports, from, to, sort, false);
    }
    public List<SearchResultDTO> searchLocations(List<String> sports, LocalDate from, LocalDate to, String sort, boolean includeSportLessOnes) {
        List<SearchResultDTO> locationsSearchResults = new ArrayList<>();
        List<LocationDTO> searchResults = new ArrayList<>(getLocations());
        searchResults.forEach(locationDTO -> {
            List<SportDTO> sportSearchResults;
            if (from == null && to == null) {
                // allow parameter-less GET request for /locations to return all records
                if (sports == null || sports.size() == 0) {
                    sportSearchResults = sportService.findSportsByLocation(locationDTO);
                } else {
                    sportSearchResults = sportService.findSportsByLocationAndName(locationDTO, sports);
                }
            } else {
                // allow parameter-less GET request for /locations to return all records
                if (sports == null || sports.size() == 0) {
                    sportSearchResults = sportService.findSportsByLocationNameAndPeriod(locationDTO, SportType.getAllNames(), from, to);
                } else {
                    sportSearchResults = sportService.findSportsByLocationNameAndPeriod(locationDTO, sports, from, to);
                }
            }

            if (includeSportLessOnes || sportSearchResults.size() > 0) {
                SearchResultDTO resultDTO = new SearchResultDTO(
                        locationDTO.getId(),
                        locationDTO.getName(),
                        locationDTO.getTown().getName(),
                        sportSearchResults);
                locationsSearchResults.add(resultDTO);
            }
        });

        if (sort != null) {
            //sorting by the average of all sports at that location
            locationsSearchResults.sort((o1, o2) -> {
                List<SportDTO> s1 = new ArrayList<>(o1.getSports());
                List<SportDTO> s2 = new ArrayList<>(o2.getSports());
                Double avg1 = getAverage(s1);
                Double avg2 = getAverage(s2);

                if (sort.equals("ASC")) {
                    if (avg1 > avg2)
                        return 1;
                    else if (avg1 < avg2)
                        return -1;
                }
                else if (sort.equals("DESC")) {
                    if (avg1 > avg2)
                        return -1;
                    else if (avg1 < avg2)
                        return 1;
                }
                return 0;
            });
        }

        return locationsSearchResults;
    }

    private Double getAverage(List<SportDTO> sports) {
        Double sum = 0.0;
        for (SportDTO sport : sports) {
            sum += sport.getAvgCostPerDay();
        }
        sum /= sports.size();
        return sum;
    }
}
