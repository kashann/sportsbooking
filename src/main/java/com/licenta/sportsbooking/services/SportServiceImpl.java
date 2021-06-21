package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.converters.SportDtoToSportConverter;
import com.licenta.sportsbooking.converters.SportToSportDtoConverter;
import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.exceptions.NotFoundException;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.Sport;
import com.licenta.sportsbooking.repositories.LocationRepository;
import com.licenta.sportsbooking.repositories.SportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SportServiceImpl implements SportService {

    private final LocationRepository locationRepository;
    private final SportRepository sportRepository;
    private final SportToSportDtoConverter sportToSportDtoConverter;
    private final SportDtoToSportConverter sportDtoToSportConverter;

    @Override
    @Transactional
    public SportDTO saveSport(SportDTO sportDTO, Long locationId) {
        Sport sport = sportDtoToSportConverter.convert(sportDTO);
        sport.setLocation(locationRepository.getOne(locationId));
        Sport savedSport = sportRepository.save(sport);
        log.debug("Saved Sport Id: " + savedSport.getId());
        return sportToSportDtoConverter.convert(savedSport);
    }

    @Override
    @Transactional
    public SportDTO modifySport(SportDTO sport, Long locationId) {
        SportDTO existingSport = findById(sport.getId());
        if (existingSport != null) {
            existingSport.setName(sport.getName());
            existingSport.setStartDate(sport.getStartDate());
            existingSport.setEndDate(sport.getEndDate());
            existingSport.setAvgCostPerDay(sport.getAvgCostPerDay());
            return saveSport(existingSport, locationId);
        }
        else {
            return saveSport(sport, locationId);
        }
    }

    @Override
    public SportDTO findById(Long id) {
        Optional<Sport> sportOptional = sportRepository.findById(id);
        if (sportOptional.isEmpty()) {
            throw new NotFoundException("Sport Not Found! For ID value: " + id);
        }
        return sportToSportDtoConverter.convert(sportOptional.get());
    }

    @Override
    public List<SportDTO> findSportsByLocationNameAndPeriod(LocationDTO location, List<String> sportNames, LocalDate from, LocalDate to) {
        List<Sport> sports = sportRepository.findByLocationId(location.getId());
        List<SportDTO> result = new ArrayList<>();
        sports.forEach(sport -> {
            if (sportNames.contains(sport.getName().name()) && isOverlapping(from, to, sport.getStartDate(), sport.getEndDate())) {
                result.add(sportToSportDtoConverter.convert(sport));
            }
        });
        return result;
    }

    @Override
    public List<SportDTO> findSportsByLocationAndName(LocationDTO location, List<String> sportNames) {
        List<Sport> sports = sportRepository.findByLocationId(location.getId());
        List<SportDTO> result = new ArrayList<>();
        sports.forEach(sport -> {
            if (sportNames.contains(sport.getName().name())) {
                result.add(sportToSportDtoConverter.convert(sport));
            }
        });
        return result;
    }

    @Override
    public List<SportDTO> findSportsByLocation(LocationDTO location) {
        List<Sport> sports = sportRepository.findByLocationId(location.getId());
        List<SportDTO> result = new ArrayList<>();
        sports.forEach(sport -> result.add(sportToSportDtoConverter.convert(sport)));
        return result;
    }

    public static boolean isOverlapping(LocalDate startQuery, LocalDate endQuery, LocalDate startSport, LocalDate endSport) {
        // the case when somebody is searching for a vacation on a fixed time period
        // ex. I can go to Ski from 15 Ian to 20 Ian, so the sport has to be available at least until 20 ian
        return !startQuery.isBefore(startSport) && !endQuery.isAfter(endSport);
    }
}
