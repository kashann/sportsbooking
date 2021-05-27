package com.licenta.sportsbooking.converters;

import com.licenta.sportsbooking.commands.LocationCommand;
import com.licenta.sportsbooking.model.Location;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LocationToLocationCommand implements Converter<Location, LocationCommand> {

    private final CountryToCountryCommand countryConverter;
    private final RegionToRegionCommand regionConverter;
    private final TownToTownCommand townConverter;
    private final SportToSportCommand sportConverter;

    public LocationToLocationCommand(CountryToCountryCommand countryConverter, RegionToRegionCommand regionConverter, TownToTownCommand townConverter, SportToSportCommand sportConverter) {
        this.countryConverter = countryConverter;
        this.regionConverter = regionConverter;
        this.townConverter = townConverter;
        this.sportConverter = sportConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public LocationCommand convert(Location source) {
        if (source == null) {
            return null;
        }

        final LocationCommand command = new LocationCommand();
        command.setId(source.getId());
        command.setName(source.getName());
        command.setTown(townConverter.convert(source.getTown()));

        if (source.getSports() != null && source.getSports().size() > 0){
            source.getSports()
                    .forEach(sport -> command.getSports().add(sportConverter.convert(sport)));
        }

        return command;
    }
}
