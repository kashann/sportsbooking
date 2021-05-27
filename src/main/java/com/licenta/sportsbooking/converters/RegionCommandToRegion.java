package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.RegionCommand;
import com.licenta.sportsbooking.model.Region;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RegionCommandToRegion implements Converter<RegionCommand, Region> {

    @Synchronized
    @Nullable
    @Override
    public Region convert(RegionCommand source) {
        if (source == null) {
            return null;
        }

        final Region region = new Region();
        region.setId(source.getId());
        region.setName(source.getName());

        return region;
    }
}
