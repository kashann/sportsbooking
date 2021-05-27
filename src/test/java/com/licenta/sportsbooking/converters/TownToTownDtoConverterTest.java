package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.TownDTO;
import com.licenta.sportsbooking.model.Town;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TownToTownDtoConverterTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    TownToTownDtoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new TownToTownDtoConverter();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Town()));
    }

    @Test
    void convert() {
        //given
        Town town = new Town();
        town.setId(ID_VALUE);
        town.setName(NAME);

        //when
        TownDTO townDTO = converter.convert(town);

        //then
        assertEquals(ID_VALUE, townDTO.getId());
        assertEquals(NAME, townDTO.getName());
    }
}