package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.commands.SportCommand;
import com.licenta.sportsbooking.converters.LocationToLocationCommand;
import com.licenta.sportsbooking.converters.SportCommandToSport;
import com.licenta.sportsbooking.converters.SportToSportCommand;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.Sport;
import com.licenta.sportsbooking.model.SportType;
import com.licenta.sportsbooking.repositories.SportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class SportServiceImplTest {

    SportServiceImpl service;

    @Mock
    SportRepository repository;

    @Mock
    SportCommandToSport sportCommandToSport;

    @Mock
    SportToSportCommand sportToSportCommand;

    @Mock
    LocationToLocationCommand locationToLocationCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new SportServiceImpl(repository, sportCommandToSport, sportToSportCommand);
    }

    @Test
    void findSportsByLocationNameAndPeriodTest() {
        Location location = new Location();
        location.setId(2L);
        Sport sport = new Sport();
        sport.setId(1L);
        sport.setName(SportType.ATV);
        sport.setLocation(location);
        sport.setStartDate(LocalDate.of(2020, 1, 1));
        sport.setEndDate(LocalDate.of(2021, 1, 1));
        List<Sport> sportList = new ArrayList<>();
        sportList.add(sport);
        List<SportType> sportTypes = new ArrayList<>();
        sportTypes.add(SportType.ATV);

        when(repository.findByLocationId(anyLong())).thenReturn(sportList);

        Set<SportCommand> sportsReturned = service
                .findSportsByLocationNameAndPeriod(locationToLocationCommand.convert(location), sportTypes,
                LocalDate.of(2020, 6, 20), LocalDate.of(2020, 6, 24));
        SportCommand sportReturned = sportsReturned.iterator().next();

        //todo fix. Manual testing working, mock failing
        assertNotNull(sportReturned, "Null sport returned");
        assertEquals(1L, sportReturned.getId());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, never()).findAll();
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