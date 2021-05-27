package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.commands.LocationCommand;
import com.licenta.sportsbooking.converters.LocationCommandToLocation;
import com.licenta.sportsbooking.converters.LocationToLocationCommand;
import com.licenta.sportsbooking.exceptions.NotFoundException;
import com.licenta.sportsbooking.mappers.LocationMapper;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.repositories.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;
    private final LocationCommandToLocation locationCommandToLocation;
    private final LocationToLocationCommand locationToLocationCommand;

    public LocationServiceImpl(LocationMapper locationMapper, LocationRepository locationRepository,
                               LocationCommandToLocation locationCommandToLocation,
                               LocationToLocationCommand locationToLocationCommand) {
        this.locationMapper = locationMapper;
        this.locationRepository = locationRepository;
        this.locationCommandToLocation = locationCommandToLocation;
        this.locationToLocationCommand = locationToLocationCommand;
    }

    @Override
    public LocationCommand findById(Long id) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        if(!locationOptional.isPresent()) {
            throw new NotFoundException("Location Not Found! For ID value: " + id);
        }
        return locationToLocationCommand.convert(locationOptional.get());
    }

    @Override
    public List<LocationCommand> getLocations() {
        log.debug("I'm getting all locations");

        return locationRepository.findAll()
                .stream()
                .map(locationMapper::locationToLocationCommand)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LocationCommand saveLocationCommand(LocationCommand command) {
        Location detachedLocation = locationCommandToLocation.convert(command);

        Location savedLocation = locationRepository.save(detachedLocation);
        log.debug("Saved Location Id: " + savedLocation.getId());
        return locationToLocationCommand.convert(savedLocation);
    }

    @Override
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }
}
