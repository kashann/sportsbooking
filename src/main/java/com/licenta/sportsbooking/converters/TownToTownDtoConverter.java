package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.TownDTO;
import com.licenta.sportsbooking.model.Town;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TownToTownDtoConverter implements Converter<Town, TownDTO> {

    @Synchronized
    @Nullable
    @Override
    public TownDTO convert(Town source) {
        if (source == null) {
            return null;
        }

        final TownDTO townDTO = new TownDTO();
        townDTO.setId(source.getId());
        townDTO.setName(source.getName());

        return townDTO;
    }
}
