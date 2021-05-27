package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.converters.LocationDtoToLocationConverter;
import com.licenta.sportsbooking.converters.LocationToLocationDtoConverter;
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
    private final LocationDtoToLocationConverter locationDtoToLocationConverter;
    private final LocationToLocationDtoConverter locationToLocationDtoConverter;

    public LocationServiceImpl(LocationMapper locationMapper, LocationRepository locationRepository,
                               LocationDtoToLocationConverter locationDtoToLocationConverter,
                               LocationToLocationDtoConverter locationToLocationDtoConverter) {
        this.locationMapper = locationMapper;
        this.locationRepository = locationRepository;
        this.locationDtoToLocationConverter = locationDtoToLocationConverter;
        this.locationToLocationDtoConverter = locationToLocationDtoConverter;
    }

    @Override
    public LocationDTO findById(Long id) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        if (locationOptional.isEmpty()) {
            throw new NotFoundException("Location Not Found! For ID value: " + id);
        }
        return locationToLocationDtoConverter.convert(locationOptional.get());
    }

    @Override
    public List<LocationDTO> getLocations() {
        log.debug("I'm getting all locations");

        return locationRepository.findAll()
                .stream()
                .map(locationMapper::locationToLocationDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LocationDTO saveLocation(LocationDTO locationDTO) {
        Location detachedLocation = locationDtoToLocationConverter.convert(locationDTO);

        Location savedLocation = locationRepository.save(detachedLocation);
        log.debug("Saved Location Id: " + savedLocation.getId());
        return locationToLocationDtoConverter.convert(savedLocation);
    }

    @Override
    @Transactional
    public LocationDTO modifyLocation(LocationDTO locationDTO, Long id) {
        LocationDTO existingLocation = findById(id);
        if (existingLocation != null) {
            existingLocation.setName(locationDTO.getName());
            existingLocation.setTown(locationDTO.getTown());
            existingLocation.setSports(locationDTO.getSports());
            return saveLocation(existingLocation);
        }
        else {
            locationDTO.setId(id);
            return saveLocation(locationDTO);
        }
    }

    @Override
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }
}
