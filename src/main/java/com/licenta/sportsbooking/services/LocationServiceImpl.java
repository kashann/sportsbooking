package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.converters.LocationDtoToLocationConverter;
import com.licenta.sportsbooking.converters.LocationToLocationDtoConverter;
import com.licenta.sportsbooking.dto.SearchResultDTO;
import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.exceptions.NotFoundException;
import com.licenta.sportsbooking.mappers.LocationMapper;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.SportType;
import com.licenta.sportsbooking.repositories.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;
    private final LocationDtoToLocationConverter locationDtoToLocationConverter;
    private final LocationToLocationDtoConverter locationToLocationDtoConverter;
    private final SportServiceImpl sportService;

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
                .map(locationMapper::locationToLocationDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LocationDTO saveLocation(LocationDTO locationDTO) {
        Location detachedLocation = locationDtoToLocationConverter.convert(locationDTO);

        Location savedLocation = locationRepository.save(detachedLocation);
        log.debug("Saved Location Id: " + savedLocation.getId());
        return locationToLocationDtoConverter.convert(savedLocation);
    }

    @Override
    @Transactional
    public LocationDTO modifyLocation(LocationDTO locationDTO, Long id) {
        LocationDTO existingLocation = findById(id);
        if (existingLocation != null) {
            existingLocation.setName(locationDTO.getName());
            existingLocation.setTown(locationDTO.getTown());
            existingLocation.setSports(locationDTO.getSports());
            return saveLocation(existingLocation);
        }
        else {
            locationDTO.setId(id);
            return saveLocation(locationDTO);
        }
    }

    @Override
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    public List<SearchResultDTO> searchLocations(List<String> sports, LocalDate from, LocalDate to, String sort) {
        List<SportType> sportTypes = new ArrayList<>();
        sports.forEach(sport -> {
            try {
                sportTypes.add(SportType.valueOf(sport));
            } catch(Exception e) {
                log.error(e.getMessage());
            }
        });
        List<SearchResultDTO> locationsSearchResults = new ArrayList<>();
        List<LocationDTO> searchResults = new ArrayList<>(getLocations());
        searchResults.forEach(locationDTO -> {
            Set<SportDTO> sportSearchResults = sportService.findSportsByLocationNameAndPeriod(locationDTO, sportTypes, from, to);
            if (sportSearchResults.size() > 0) {
                SearchResultDTO resultDTO = new SearchResultDTO(locationDTO.getName(), sportSearchResults);
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
        for (int i = 0; i < sports.size(); i++) {
            sum += sports.get(i).getAvgCostPerDay();
        }
        sum /= sports.size();
        return sum;
    }
}
