package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.commands.LocationCommand;

import java.util.List;

public interface LocationService {

    LocationCommand findById(Long id);
    List<LocationCommand> getLocations();
    LocationCommand saveLocationCommand(LocationCommand command);
    void deleteById(Long id);

}
