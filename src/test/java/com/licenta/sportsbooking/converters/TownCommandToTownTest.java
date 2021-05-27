package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.TownCommand;
import com.licenta.sportsbooking.model.Town;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TownCommandToTownTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    TownCommandToTown converter;

    @BeforeEach
    void setUp() {
        converter = new TownCommandToTown();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new TownCommand()));
    }

    @Test
    void convert() {
        //given
        TownCommand townCommand = new TownCommand();
        townCommand.setId(ID_VALUE);
        townCommand.setName(NAME);

        //when
        Town town = converter.convert(townCommand);

        //then
        assertEquals(ID_VALUE, town.getId());
        assertEquals(NAME, town.getName());
    }
}