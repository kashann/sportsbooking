package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.LocationCommand;
import com.licenta.sportsbooking.commands.SportCommand;
import com.licenta.sportsbooking.commands.TownCommand;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.Sport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LocationCommandToLocationTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    public static final TownCommand TOWN = new TownCommand();
    public static final Set<SportCommand> SPORTS = new HashSet<>();
    LocationCommandToLocation converter;
    CountryCommandToCountry countryConverter;
    RegionCommandToRegion regionConverter;
    TownCommandToTown townConverter;
    SportCommandToSport sportConverter;

    @BeforeEach
    void setUp() {
        countryConverter = new CountryCommandToCountry();
        regionConverter = new RegionCommandToRegion();
        townConverter = new TownCommandToTown();
        sportConverter = new SportCommandToSport();
        converter = new LocationCommandToLocation(countryConverter, regionConverter, townConverter, sportConverter);
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new LocationCommand()));
    }

    @Test
    void convert() {
        //given
        LocationCommand locationCommand = new LocationCommand();
        locationCommand.setId(ID_VALUE);
        locationCommand.setName(NAME);
        locationCommand.setTown(TOWN);
        locationCommand.setSports(SPORTS);

        //when
        Location location = converter.convert(locationCommand);

        //then
        assertEquals(ID_VALUE, location.getId());
        assertEquals(NAME, location.getName());
        assertEquals(townConverter.convert(TOWN), location.getTown());
        Set<Sport> sports = new HashSet<>();
        SPORTS.forEach(sport -> sports.add(sportConverter.convert(sport)));
        assertEquals(sports, location.getSports());
    }
}