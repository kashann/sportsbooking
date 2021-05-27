package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.converters.LocationDtoToLocationConverter;
import com.licenta.sportsbooking.converters.LocationToLocationDtoConverter;
import com.licenta.sportsbooking.exceptions.NotFoundException;
import com.licenta.sportsbooking.mappers.LocationMapper;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.repositories.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class LocationServiceImplTest {

    @Autowired
    LocationServiceImpl service;

    @Autowired
    LocationToLocationDtoConverter locationToLocationDtoConverter;

    @Test
    void findById() {
        Location location = new Location();
        location.setId(1L);
        service.saveLocation(locationToLocationDtoConverter.convert(location));

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
        ArrayList<Location> locationsData = new ArrayList();
        locationsData.add(location);
        LocationDTO initialLocationDto = locationToLocationDtoConverter.convert(location);

        service.saveLocation(initialLocationDto);

        List<LocationDTO> locations = service.getLocations();

        boolean found = false;
        for(LocationDTO locationDTO : locations) {
            if (locationDTO.equals(initialLocationDto)) {
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    void saveLocation() {
        Location location = new Location();
        location.setId(1L);
        service.saveLocation(locationToLocationDtoConverter.convert(location));

        LocationDTO locationReturned = service.saveLocation(locationToLocationDtoConverter.convert(location));

        assertNotNull(locationReturned, "Null location returned");
        assertEquals(1L, locationReturned.getId());
    }

    @Test
    void deleteById() {
        //given
        Long idToDelete = 3L;
        Location location = new Location();
        location.setId(idToDelete);
        service.saveLocation(locationToLocationDtoConverter.convert(location));

        //when
        service.deleteById(idToDelete);

        //no 'when' because method has void return type

        //then
        assertThrows(NotFoundException.class, () -> {
            service.findById(idToDelete);
        });
    }
}