package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.CountryCommand;
import com.licenta.sportsbooking.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryToCountryCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    CountryToCountryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CountryToCountryCommand();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Country()));
    }

    @Test
    void convert() {
        //given
        Country country = new Country();
        country.setId(ID_VALUE);
        country.setName(NAME);

        //when
        CountryCommand countryCommand = converter.convert(country);

        //then
        assertEquals(ID_VALUE, countryCommand.getId());
        assertEquals(NAME, countryCommand.getName());
    }
}