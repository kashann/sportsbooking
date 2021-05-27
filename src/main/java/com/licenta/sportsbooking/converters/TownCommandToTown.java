package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.TownCommand;
import com.licenta.sportsbooking.model.Town;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TownCommandToTown implements Converter<TownCommand, Town> {

    @Synchronized
    @Nullable
    @Override
    public Town convert(TownCommand source) {
        if (source == null) {
            return null;
        }

        final Town town = new Town();
        town.setId(source.getId());
        town.setName(source.getName());

        return town;
    }
}
