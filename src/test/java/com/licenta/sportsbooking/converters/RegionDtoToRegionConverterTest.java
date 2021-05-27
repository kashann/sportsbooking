package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.RegionDTO;
import com.licenta.sportsbooking.model.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegionDtoToRegionConverterTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    RegionDtoToRegionConverter converter;

    @BeforeEach
    void setUp() {
        converter = new RegionDtoToRegionConverter();
    }


    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new RegionDTO()));
    }

    @Test
    void convert() {
        //given
        RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(ID_VALUE);
        regionDTO.setName(NAME);

        //when
        Region region = converter.convert(regionDTO);

        //then
        assertEquals(ID_VALUE, region.getId());
        assertEquals(NAME, region.getName());
    }
}