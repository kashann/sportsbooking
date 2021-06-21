package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.converters.LocationDtoToLocationConverter;
import com.licenta.sportsbooking.converters.LocationToLocationDtoConverter;
import com.licenta.sportsbooking.converters.SportToSportDtoConverter;
import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.Sport;
import com.licenta.sportsbooking.model.SportType;
import com.licenta.sportsbooking.repositories.LocationRepository;
import com.licenta.sportsbooking.repositories.SportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SportServiceImplTest {

    @Autowired
    SportServiceImpl sportService;

    @Autowired
    LocationServiceImpl locationService;

    @Autowired
    LocationToLocationDtoConverter locationToLocationDtoConverter;

    @Autowired
    LocationDtoToLocationConverter locationDtoToLocationConverter;

    @Autowired
    TownService townService;

    @Autowired
    SportRepository sportRepository;

    @Test
    void findSportsByLocationNameAndPeriodTest() {
        SportType sportType = SportType.ATV;
        Location location = new Location();
        Sport sport = new Sport();
        sport.setName(sportType);
        sport.setStartDate(LocalDate.of(2021, 1, 1));
        sport.setEndDate(LocalDate.of(2022, 1, 1));

        LocationDTO locationDTO = locationToLocationDtoConverter.convert(location);
        locationDTO.setTown(townService.getTowns().get(0));
        LocationDTO savedLocation = locationService.saveLocation(locationDTO, locationDTO.getTown().getId());
        sport.setLocation(locationDtoToLocationConverter.convert(savedLocation));
        sportRepository.save(sport);

        List<String> sportTypes = new ArrayList<>();
        sportTypes.add(sportType.name());

        List<SportDTO> sportsReturned = sportService
                .findSportsByLocationNameAndPeriod(Objects.requireNonNull(savedLocation), sportTypes,
                LocalDate.of(2021, 6, 20), LocalDate.of(2021, 6, 24));
        assertFalse(sportsReturned.isEmpty());
        SportDTO sportReturned = sportsReturned.iterator().next();

        assertNotNull(sportReturned, "Null sport returned");
        assertEquals(sportType.name(), sportReturned.getName());
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