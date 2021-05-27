package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.SportCommand;
import com.licenta.sportsbooking.model.Sport;
import com.licenta.sportsbooking.model.SportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SportCommandToSportTest {

    public static final Long ID_VALUE = 1L;
    public static final SportType NAME = SportType.DOWNHILL;
    public static final LocalDate START = LocalDate.now();
    public static final LocalDate END = LocalDate.ofYearDay(2020, 300);
    public static final Double PRICE = 50.0;
    SportCommandToSport converter;

    @BeforeEach
    void setUp() {
        converter = new SportCommandToSport();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new SportCommand()));
    }

    @Test
    void convert() {
        //given
        SportCommand sportCommand = new SportCommand();
        sportCommand.setId(ID_VALUE);
        sportCommand.setName(NAME);
        sportCommand.setStartDate(START);
        sportCommand.setEndDate(END);
        sportCommand.setAvgCostPerDay(PRICE);

        //when
        Sport sport = converter.convert(sportCommand);

        //then
        assertEquals(ID_VALUE, sport.getId());
        assertEquals(NAME, sport.getName());
        assertEquals(START, sport.getStartDate());
        assertEquals(END, sport.getEndDate());
        assertEquals(PRICE, sport.getAvgCostPerDay());
    }
}