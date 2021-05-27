package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.RegionCommand;
import com.licenta.sportsbooking.model.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegionToRegionCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    RegionToRegionCommand converter;

    @BeforeEach
    void setUp() {
        converter = new RegionToRegionCommand();
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
        RegionCommand regionCommand = converter.convert(region);

        //then
        assertEquals(ID_VALUE, regionCommand.getId());
        assertEquals(NAME, regionCommand.getName());
    }
}