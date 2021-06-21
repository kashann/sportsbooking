package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.model.Location;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationDtoToLocationConverter implements Converter<LocationDTO, Location> {

    private final SportDtoToSportConverter sportConverter;

    @Synchronized
    @Nullable
    @Override
    public Location convert(LocationDTO source) {
        if (source == null) {
            return null;
        }

        final Location location = new Location();
        location.setId(source.getId());
        location.setName(source.getName());

        if (source.getSports() != null && source.getSports().size() > 0) {
            source.getSports()
                    .forEach(sport -> location.getSports().add(sportConverter.convert(sport)));
        }

        return location;
    }
}
