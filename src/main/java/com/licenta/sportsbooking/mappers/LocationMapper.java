package com.licenta.sportsbooking.mappers;

import com.licenta.sportsbooking.commands.LocationCommand;
import com.licenta.sportsbooking.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    LocationCommand locationToLocationCommand(Location location);
}
