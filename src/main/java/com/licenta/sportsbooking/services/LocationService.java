package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.dto.LocationDTO;

import java.util.List;

public interface LocationService {

    LocationDTO findById(Long id);
    List<LocationDTO> getLocations();
    LocationDTO saveLocation(LocationDTO locationDTO);
    LocationDTO modifyLocation(LocationDTO locationDTO, Long id);
    void deleteById(Long id);

}
