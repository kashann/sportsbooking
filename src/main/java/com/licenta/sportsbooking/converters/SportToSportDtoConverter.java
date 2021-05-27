package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.model.Sport;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SportToSportDtoConverter implements Converter<Sport, SportDTO> {

    @Synchronized
    @Nullable
    @Override
    public SportDTO convert(Sport source) {
        if (source == null) {
            return null;
        }

        final SportDTO sportDTO = new SportDTO();
        sportDTO.setId(source.getId());
        sportDTO.setName(source.getName());
        sportDTO.setStartDate(source.getStartDate());
        sportDTO.setEndDate(source.getEndDate());
        sportDTO.setAvgCostPerDay(source.getAvgCostPerDay());

        return sportDTO;
    }
}
