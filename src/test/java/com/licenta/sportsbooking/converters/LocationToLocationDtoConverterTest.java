package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.Sport;
import com.licenta.sportsbooking.model.Town;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LocationToLocationDtoConverterTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    public static final Town TOWN = new Town();
    public static final List<Sport> SPORTS = new ArrayList<>();
    LocationToLocationDtoConverter converter;
    TownToTownDtoConverter townConverter;
    SportToSportDtoConverter sportConverter;

    @BeforeEach
    void setUp() {
        townConverter = new TownToTownDtoConverter();
        sportConverter = new SportToSportDtoConverter();
        converter = new LocationToLocationDtoConverter(townConverter, sportConverter);
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
        LocationDTO locationDTO = converter.convert(location);

        //then
        assertEquals(ID_VALUE, locationDTO.getId());
        assertEquals(NAME, locationDTO.getName());
        assertEquals(townConverter.convert(TOWN), locationDTO.getTown());
        Set<SportDTO> sports = new HashSet<>();
        SPORTS.forEach(sport -> sports.add(sportConverter.convert(sport)));
        assertEquals(sports.size(), locationDTO.getSports().size());
    }
}