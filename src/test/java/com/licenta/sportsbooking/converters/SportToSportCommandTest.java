package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.SportCommand;
import com.licenta.sportsbooking.model.Sport;
import com.licenta.sportsbooking.model.SportType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SportToSportCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final SportType NAME = SportType.DOWNHILL;
    public static final LocalDate START = LocalDate.now();
    public static final LocalDate END = LocalDate.ofYearDay(2020, 300);
    public static final Double PRICE = 50.0;
    SportToSportCommand converter;

    @BeforeEach
    void setUp() {
        converter = new SportToSportCommand();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Sport()));
    }

    @Test
    void convert() {
        //given
        Sport sport = new Sport();
        sport.setId(ID_VALUE);
        sport.setName(NAME);
        sport.setStartDate(START);
        sport.setEndDate(END);
        sport.setAvgCostPerDay(PRICE);

        //when
        SportCommand sportCommand = converter.convert(sport);

        //then
        assertEquals(ID_VALUE, sportCommand.getId());
        Assertions.assertEquals(NAME, sportCommand.getName());
        assertEquals(START, sportCommand.getStartDate());
        assertEquals(END, sportCommand.getEndDate());
        assertEquals(PRICE, sportCommand.getAvgCostPerDay());
    }
}