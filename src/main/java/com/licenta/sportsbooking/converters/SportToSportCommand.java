package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.SportCommand;
import com.licenta.sportsbooking.model.Sport;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SportToSportCommand implements Converter<Sport, SportCommand> {

    @Synchronized
    @Nullable
    @Override
    public SportCommand convert(Sport source) {
        if (source == null) {
            return null;
        }

        final SportCommand command = new SportCommand();
        command.setId(source.getId());
        command.setName(source.getName());
        command.setStartDate(source.getStartDate());
        command.setEndDate(source.getEndDate());
        command.setAvgCostPerDay(source.getAvgCostPerDay());

        return command;
    }
}
