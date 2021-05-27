package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.RegionCommand;
import com.licenta.sportsbooking.model.Region;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RegionToRegionCommand implements Converter<Region, RegionCommand> {

    @Synchronized
    @Nullable
    @Override
    public RegionCommand convert(Region source) {
        if (source == null) {
            return null;
        }

        final RegionCommand command = new RegionCommand();
        command.setId(source.getId());
        command.setName(source.getName());

        return command;
    }
}
