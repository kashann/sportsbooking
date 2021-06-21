package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.dto.TownDTO;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.Sport;
import com.licenta.sportsbooking.repositories.TownRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LocationDtoToLocationConverterTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    public static final TownDTO TOWN = new TownDTO();
    public static final List<SportDTO> SPORTS = new ArrayList<>();
    LocationDtoToLocationConverter converter;
    TownDtoToTownConverter townConverter;
    SportDtoToSportConverter sportConverter;

    @BeforeEach
    void setUp() {
        townConverter = new TownDtoToTownConverter();
        sportConverter = new SportDtoToSportConverter();
        converter = new LocationDtoToLocationConverter(sportConverter);
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new LocationDTO()));
    }

    @Test
    void convert() {
        //given
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(ID_VALUE);
        locationDTO.setName(NAME);
        locationDTO.setTown(TOWN);
        locationDTO.setSports(SPORTS);

        //when
        Location location = converter.convert(locationDTO);

        //then
        assertEquals(ID_VALUE, location.getId());
        assertEquals(NAME, location.getName());
        Set<Sport> sports = new HashSet<>();
        SPORTS.forEach(sport -> sports.add(sportConverter.convert(sport)));
        assertEquals(sports.size(), location.getSports().size());
    }
}