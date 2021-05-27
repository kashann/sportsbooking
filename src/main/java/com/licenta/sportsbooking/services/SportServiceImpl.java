package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.converters.SportToSportDtoConverter;
import com.licenta.sportsbooking.model.Sport;
import com.licenta.sportsbooking.model.SportType;
import com.licenta.sportsbooking.repositories.SportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SportServiceImpl implements SportService {

    private final SportRepository sportRepository;
    private final SportToSportDtoConverter sportToSportDtoConverter;

    @Override
    public Set<SportDTO> findSportsByLocationNameAndPeriod(LocationDTO location, List<SportType> names, LocalDate from, LocalDate to) {
        List<Sport> sports = sportRepository.findByLocationId(location.getId());
        Set<SportDTO> result = new HashSet<>();
        sports.forEach(sport -> {
            if (names.contains(sport.getName()) && isOverlapping(from, to, sport.getStartDate(), sport.getEndDate())) {
                result.add(sportToSportDtoConverter.convert(sport));
            }
        });
        return result;
    }

    public static boolean isOverlapping(LocalDate startQuery, LocalDate endQuery, LocalDate startSport, LocalDate endSport) {
        // the case when somebody is searching for a vacation on a fixed time period
        // ex. I can go to Ski from 15 Ian to 20 Ian, so the sport has to be available at least until 20 ian
        return !startQuery.isBefore(startSport) && !endQuery.isAfter(endSport);
    }
}
