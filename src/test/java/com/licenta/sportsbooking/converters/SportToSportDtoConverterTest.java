package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.model.Sport;
import com.licenta.sportsbooking.model.SportType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SportToSportDtoConverterTest {

    public static final Long ID_VALUE = 1L;
    public static final SportType NAME = SportType.DOWNHILL;
    public static final LocalDate START = LocalDate.now();
    public static final LocalDate END = LocalDate.ofYearDay(2020, 300);
    public static final Double PRICE = 50.0;
    SportToSportDtoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new SportToSportDtoConverter();
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
        SportDTO sportDTO = converter.convert(sport);

        //then
        assertEquals(ID_VALUE, sportDTO.getId());
        Assertions.assertEquals(NAME, sportDTO.getName());
        assertEquals(START, sportDTO.getStartDate());
        assertEquals(END, sportDTO.getEndDate());
        assertEquals(PRICE, sportDTO.getAvgCostPerDay());
    }
}