package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.model.Sport;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SportDtoToSportConverter implements Converter<SportDTO, Sport> {

    @Synchronized
    @Nullable
    @Override
    public Sport convert(SportDTO source) {
        if (source == null) {
            return null;
        }

        final Sport sport = new Sport();
        sport.setId(source.getId());
        sport.setName(source.getName());
        sport.setStartDate(source.getStartDate());
        sport.setEndDate(source.getEndDate());
        sport.setAvgCostPerDay(source.getAvgCostPerDay());

        return sport;
    }
}
