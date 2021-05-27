package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.RegionCommand;
import com.licenta.sportsbooking.model.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegionCommandToRegionTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    RegionCommandToRegion converter;

    @BeforeEach
    void setUp() {
        converter = new RegionCommandToRegion();
    }


    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new RegionCommand()));
    }

    @Test
    void convert() {
        //given
        RegionCommand regionCommand = new RegionCommand();
        regionCommand.setId(ID_VALUE);
        regionCommand.setName(NAME);

        //when
        Region region = converter.convert(regionCommand);

        //then
        assertEquals(ID_VALUE, region.getId());
        assertEquals(NAME, region.getName());
    }
}