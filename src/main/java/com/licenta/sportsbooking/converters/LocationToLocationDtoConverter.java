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
public class LocationToLocationDtoConverter implements Converter<Location, LocationDTO> {

    private final TownToTownDtoConverter townConverter;
    private final SportToSportDtoConverter sportConverter;

    @Synchronized
    @Nullable
    @Override
    public LocationDTO convert(Location source) {
        if (source == null) {
            return null;
        }

        final LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(source.getId());
        locationDTO.setName(source.getName());
        locationDTO.setTown(townConverter.convert(source.getTown()));

        if (source.getSports() != null && source.getSports().size() > 0) {
            source.getSports()
                    .forEach(sport -> locationDTO.getSports().add(sportConverter.convert(sport)));
        }

        return locationDTO;
    }
}
