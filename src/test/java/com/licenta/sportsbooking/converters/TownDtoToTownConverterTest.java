package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.TownDTO;
import com.licenta.sportsbooking.model.Town;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TownDtoToTownConverterTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    TownDtoToTownConverter converter;

    @BeforeEach
    void setUp() {
        converter = new TownDtoToTownConverter();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new TownDTO()));
    }

    @Test
    void convert() {
        //given
        TownDTO townDTO = new TownDTO();
        townDTO.setId(ID_VALUE);
        townDTO.setName(NAME);

        //when
        Town town = converter.convert(townDTO);

        //then
        assertEquals(ID_VALUE, town.getId());
        assertEquals(NAME, town.getName());
    }
}