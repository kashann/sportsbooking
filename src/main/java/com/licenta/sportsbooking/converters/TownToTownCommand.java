package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.TownCommand;
import com.licenta.sportsbooking.model.Town;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TownToTownCommand implements Converter<Town, TownCommand> {

    @Synchronized
    @Nullable
    @Override
    public TownCommand convert(Town source) {
        if (source == null) {
            return null;
        }

        final TownCommand command = new TownCommand();
        command.setId(source.getId());
        command.setName(source.getName());

        return command;
    }
}
