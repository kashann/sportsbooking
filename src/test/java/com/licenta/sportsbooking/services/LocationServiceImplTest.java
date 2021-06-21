package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.converters.LocationToLocationDtoConverter;
import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.exceptions.NotFoundException;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.Town;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationServiceImplTest {

    @Autowired
    LocationServiceImpl service;

    @Autowired
    LocationToLocationDtoConverter locationToLocationDtoConverter;

    @Autowired
    TownService townService;

    @Test
    void findById() {
        Location location = new Location();
        location.setId(1L);
        LocationDTO locationDTO = locationToLocationDtoConverter.convert(location);
        locationDTO.setTown(townService.getTowns().get(0));
        service.saveLocation(locationDTO, locationDTO.getTown().getId());

        LocationDTO locationReturned = service.findById(1L);

        assertNotNull(locationReturned, "Null location returned");
        assertEquals(1L, locationReturned.getId());
    }

    @Test
    void notFoundById() {
        assertThrows(NotFoundException.class, () -> {
            service.findById(999L);
        });
    }

    @Test
    void getLocations() {
        Location location = new Location();
        location.setName("TEST GET LOCATIONS");
        LocationDTO initialLocationDto = locationToLocationDtoConverter.convert(location);
        initialLocationDto.setTown(townService.getTowns().get(0));

        service.saveLocation(initialLocationDto, initialLocationDto.getTown().getId());

        List<LocationDTO> locations = service.getLocations();

        boolean found = false;
        for (LocationDTO locationDTO : locations) {
            if (locationDTO.getName() != null && locationDTO.getName().equals(initialLocationDto.getName())) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    void saveLocation() {
        Location location = new Location();
        location.setId(1L);
        LocationDTO locationDTO = locationToLocationDtoConverter.convert(location);
        locationDTO.setTown(townService.getTowns().get(0));
        service.saveLocation(locationDTO, locationDTO.getTown().getId());

        LocationDTO locationReturned = service.saveLocation(locationDTO, locationDTO.getTown().getId());

        assertNotNull(locationReturned, "Null location returned");
        assertEquals(1L, locationReturned.getId());
    }

    @Test
    void deleteById() {
        //given
        Long idToDelete = 3L;
        Location location = new Location();
        location.setId(idToDelete);
        LocationDTO locationDTO = locationToLocationDtoConverter.convert(location);
        locationDTO.setTown(townService.getTowns().get(0));
        service.saveLocation(locationDTO, locationDTO.getTown().getId());

        //when
        service.deleteById(idToDelete);

        //no 'when' because method has void return type

        //then
        assertThrows(NotFoundException.class, () -> {
            service.findById(idToDelete);
        });
    }
}