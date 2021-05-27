package com.licenta.sportsbooking.mappers;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    LocationDTO locationToLocationDto(Location location);
}
