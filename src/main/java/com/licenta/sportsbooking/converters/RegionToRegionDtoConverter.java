package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.RegionDTO;
import com.licenta.sportsbooking.model.Region;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RegionToRegionDtoConverter implements Converter<Region, RegionDTO> {

    @Synchronized
    @Nullable
    @Override
    public RegionDTO convert(Region source) {
        if (source == null) {
            return null;
        }

        final RegionDTO regionDTO = new RegionDTO();
        regionDTO.setId(source.getId());
        regionDTO.setName(source.getName());

        return regionDTO;
    }
}
