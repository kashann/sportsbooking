package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.converters.LocationToLocationDtoConverter;
import com.licenta.sportsbooking.converters.SportDtoToSportConverter;
import com.licenta.sportsbooking.converters.SportToSportDtoConverter;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.Sport;
import com.licenta.sportsbooking.model.SportType;
import com.licenta.sportsbooking.repositories.LocationRepository;
import com.licenta.sportsbooking.repositories.SportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class SportServiceImplTest {

    @Autowired
    SportServiceImpl sportService;

    @Autowired
    LocationServiceImpl locationService;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    LocationToLocationDtoConverter locationToLocationDtoConverter;

    @Test
    void findSportsByLocationNameAndPeriodTest() {
        SportType sportType = SportType.ATV;
        Location location = new Location();
        Sport sport = new Sport();
        sport.setName(sportType);
        sport.setStartDate(LocalDate.of(2021, 1, 1));
        sport.setEndDate(LocalDate.of(2022, 1, 1));
        sport.setLocation(location);

        locationService.saveLocation(locationToLocationDtoConverter.convert(location));

        List<SportType> sportTypes = new ArrayList<>();
        sportTypes.add(sportType);

        Set<SportDTO> sportsReturned = sportService
                .findSportsByLocationNameAndPeriod(Objects.requireNonNull(locationToLocationDtoConverter.convert(location)), sportTypes,
                LocalDate.of(2021, 6, 20), LocalDate.of(2021, 6, 24));
        assertFalse(sportsReturned.isEmpty());
        SportDTO sportReturned = sportsReturned.iterator().next();

        assertNotNull(sportReturned, "Null sport returned");
        assertEquals(sportType, sportReturned.getName());
    }

    @Test
    void isOverlappingTest() {
        LocalDate may1 = LocalDate.of(2020, 5, 1);
        LocalDate may15 = LocalDate.of(2020, 5,15);
        LocalDate june1 = LocalDate.of(2020, 6, 1);
        LocalDate june30 = LocalDate.of(2020, 6, 30);

        // Overlapping
        Assertions.assertTrue(SportServiceImpl.isOverlapping(may15, june1, may1, june30));
        // Not Overlapping
        Assertions.assertFalse(SportServiceImpl.isOverlapping(may1, may15, june1, june30));
    }
}