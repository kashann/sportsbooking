package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.CountryDTO;
import com.licenta.sportsbooking.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryDtoToCountryConverterTest {

    public static final Long ID_VALUE = 1L;
    public static final String NAME = "test name";
    CountryDtoToCountryConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CountryDtoToCountryConverter();

    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new CountryDTO()));
    }

    @Test
    void convert() {
        //given
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(ID_VALUE);
        countryDTO.setName(NAME);

        //when
        Country country = converter.convert(countryDTO);

        //then
        assertEquals(ID_VALUE, country.getId());
        assertEquals(NAME, country.getName());
    }
}