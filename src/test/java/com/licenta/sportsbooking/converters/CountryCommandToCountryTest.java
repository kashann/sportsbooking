package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.CountryCommand;
import com.licenta.sportsbooking.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryCommandToCountryTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    CountryCommandToCountry converter;

    @BeforeEach
    void setUp() {
        converter = new CountryCommandToCountry();

    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new CountryCommand()));
    }

    @Test
    void convert() {
        //given
        CountryCommand countryCommand = new CountryCommand();
        countryCommand.setId(ID_VALUE);
        countryCommand.setName(NAME);

        //when
        Country country = converter.convert(countryCommand);

        //then
        assertEquals(ID_VALUE, country.getId());
        assertEquals(NAME, country.getName());
    }
}