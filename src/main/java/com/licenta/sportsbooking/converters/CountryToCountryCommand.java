package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.CountryCommand;
import com.licenta.sportsbooking.model.Country;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CountryToCountryCommand implements Converter<Country, CountryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CountryCommand convert(Country source) {
        if (source == null) {
            return null;
        }

        final CountryCommand command = new CountryCommand();
        command.setId(source.getId());
        command.setName(source.getName());

        return command;
    }
}
