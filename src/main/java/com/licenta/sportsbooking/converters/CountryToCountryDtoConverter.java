package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.CountryDTO;
import com.licenta.sportsbooking.model.Country;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CountryToCountryDtoConverter implements Converter<Country, CountryDTO> {

    @Synchronized
    @Nullable
    @Override
    public CountryDTO convert(Country source) {
        if (source == null) {
            return null;
        }

        final CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(source.getId());
        countryDTO.setName(source.getName());

        return countryDTO;
    }
}
