package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.TownDTO;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.Town;
import com.licenta.sportsbooking.repositories.TownRepository;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TownDtoToTownConverter implements Converter<TownDTO, Town> {


    @Synchronized
    @Nullable
    @Override
    public Town convert(TownDTO source) {
        if (source == null) {
            return null;
        }

        final Town town = new Town();
        town.setId(source.getId());
        town.setName(source.getName());

        return town;
    }
}
