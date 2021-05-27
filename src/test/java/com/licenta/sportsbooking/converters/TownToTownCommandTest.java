package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.TownCommand;
import com.licenta.sportsbooking.model.Town;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TownToTownCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    TownToTownCommand converter;

    @BeforeEach
    void setUp() {
        converter = new TownToTownCommand();
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
        TownCommand townCommand = converter.convert(town);

        //then
        assertEquals(ID_VALUE, townCommand.getId());
        assertEquals(NAME, townCommand.getName());
    }
}