package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.LocationCommand;
import com.licenta.sportsbooking.model.Location;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LocationCommandToLocation implements Converter<LocationCommand, Location> {

    private final CountryCommandToCountry countryConverter;
    private final RegionCommandToRegion regionConverter;
    private final TownCommandToTown townConverter;
    private final SportCommandToSport sportConverter;

    public LocationCommandToLocation(CountryCommandToCountry countryConverter, RegionCommandToRegion regionConverter, TownCommandToTown townConverter, SportCommandToSport sportConverter) {
        this.countryConverter = countryConverter;
        this.regionConverter = regionConverter;
        this.townConverter = townConverter;
        this.sportConverter = sportConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Location convert(LocationCommand source) {
        if (source == null) {
            return null;
        }

        final Location location = new Location();
        location.setId(source.getId());
        location.setName(source.getName());
        location.setTown(townConverter.convert(source.getTown()));

        if (source.getSports() != null && source.getSports().size() > 0){
            source.getSports()
                    .forEach(sport -> location.getSports().add(sportConverter.convert(sport)));
        }

        return location;
    }
}
