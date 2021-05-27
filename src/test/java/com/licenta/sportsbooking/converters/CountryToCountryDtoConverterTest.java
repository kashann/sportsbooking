package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.CountryDTO;
import com.licenta.sportsbooking.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryToCountryDtoConverterTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    CountryToCountryDtoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CountryToCountryDtoConverter();
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
        CountryDTO countryDTO = converter.convert(country);

        //then
        assertEquals(ID_VALUE, countryDTO.getId());
        assertEquals(NAME, countryDTO.getName());
    }
}