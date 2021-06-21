package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.dto.LocationDTO;

import java.util.List;

public interface LocationService {

    LocationDTO findById(Long id);
    List<LocationDTO> getLocations();
    LocationDTO saveLocation(LocationDTO locationDTO, Long townId);
    LocationDTO modifyLocation(LocationDTO locationDTO, Long townId);
    void deleteById(Long id);

}
