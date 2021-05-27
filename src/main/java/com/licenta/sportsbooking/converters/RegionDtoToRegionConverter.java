package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.RegionDTO;
import com.licenta.sportsbooking.model.Region;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RegionDtoToRegionConverter implements Converter<RegionDTO, Region> {

    @Synchronized
    @Nullable
    @Override
    public Region convert(RegionDTO source) {
        if (source == null) {
            return null;
        }

        final Region region = new Region();
        region.setId(source.getId());
        region.setName(source.getName());

        return region;
    }
}
