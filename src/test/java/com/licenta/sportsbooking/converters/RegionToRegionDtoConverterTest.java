package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.RegionDTO;
import com.licenta.sportsbooking.model.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegionToRegionDtoConverterTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    RegionToRegionDtoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new RegionToRegionDtoConverter();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Region()));
    }

    @Test
    void convert() {
        //given
        Region region = new Region();
        region.setId(ID_VALUE);
        region.setName(NAME);

        //when
        RegionDTO regionDTO = converter.convert(region);

        //then
        assertEquals(ID_VALUE, regionDTO.getId());
        assertEquals(NAME, regionDTO.getName());
    }
}