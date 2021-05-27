package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.LocationCommand;
import com.licenta.sportsbooking.commands.SportCommand;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.Sport;
import com.licenta.sportsbooking.model.Town;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LocationToLocationCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    public static final Town TOWN = new Town();
    public static final Set<Sport> SPORTS = new HashSet<>();
    LocationToLocationCommand converter;
    CountryToCountryCommand countryConverter;
    RegionToRegionCommand regionConverter;
    TownToTownCommand townConverter;
    SportToSportCommand sportConverter;

    @BeforeEach
    void setUp() {
        countryConverter = new CountryToCountryCommand();
        regionConverter = new RegionToRegionCommand();
        townConverter = new TownToTownCommand();
        sportConverter = new SportToSportCommand();
        converter = new LocationToLocationCommand(countryConverter, regionConverter, townConverter, sportConverter);
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Location()));
    }

    @Test
    void convert() {
        //given
        Location location = new Location();
        location.setId(ID_VALUE);
        location.setName(NAME);
        location.setTown(TOWN);
        location.setSports(SPORTS);

        //when
        LocationCommand locationCommand = converter.convert(location);

        //then
        assertEquals(ID_VALUE, locationCommand.getId());
        assertEquals(NAME, locationCommand.getName());
        assertEquals(townConverter.convert(TOWN), locationCommand.getTown());
        Set<SportCommand> sports = new HashSet<>();
        SPORTS.forEach(sport -> sports.add(sportConverter.convert(sport)));
        assertEquals(sports, locationCommand.getSports());
    }
}