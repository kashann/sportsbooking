package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.commands.LocationCommand;
import com.licenta.sportsbooking.converters.LocationCommandToLocation;
import com.licenta.sportsbooking.converters.LocationToLocationCommand;
import com.licenta.sportsbooking.exceptions.NotFoundException;
import com.licenta.sportsbooking.mappers.LocationMapper;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.repositories.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class LocationServiceImplTest {

    LocationServiceImpl service;

    @Mock
    LocationMapper locationMapper;

    @Mock
    LocationRepository repository;

    @Mock
    LocationCommandToLocation locationCommandToLocation;

    @Mock
    LocationToLocationCommand locationToLocationCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new LocationServiceImpl(locationMapper, repository, locationCommandToLocation, locationToLocationCommand);
    }

    @Test
    void findById() {
        Location location = new Location();
        location.setId(1L);
        Optional<Location> locationOptional = Optional.of(location);

        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(location));

        LocationCommand locationReturned = service.findById(1L);

        //todo fix. Manual testing working, mock failing
        assertNotNull(locationReturned, "Null location returned");
        assertEquals(1L, locationReturned.getId());
        verify(repository, times(1)).findById(anyLong());
        verify(repository, never()).findAll();
    }

    @Test
    void notFoundById() {
        Optional<Location> locationOptional = Optional.empty();

        when(repository.findById(anyLong())).thenReturn(locationOptional);

        assertThrows(NotFoundException.class, () -> {
            Location locationReturned = locationCommandToLocation.convert(service.findById(1L));
        });
    }

    @Test
    void getLocations() {
        Location location = new Location();
        ArrayList<Location> locationsData = new ArrayList();
        locationsData.add(location);

        when(repository.findAll()).thenReturn(locationsData);

        List<LocationCommand> locations = service.getLocations();

        assertEquals(locations.size(), 1);
        verify(repository, times(1)).findAll();
        verify(repository, never()).findById(anyLong());
    }

    @Test
    void saveLocationCommand() {
        Location location = new Location();
        location.setId(1L);
        Optional<Location> locationOptional = Optional.of(location);

        when(locationCommandToLocation.convert(any())).thenReturn(location);
        when(repository.save(any())).thenReturn(location);

        LocationCommand locationReturned = service.saveLocationCommand(locationToLocationCommand.convert(location));

        //todo fix. Manual testing working, mock failing
        assertNotNull(locationReturned, "Null location returned");
        assertEquals(1L, locationReturned.getId());
        verify(repository, times(1)).save(any(Location.class));

//        //given
//        CustomerDTO customerDTO = new CustomerDTO();
//        customerDTO.setFirstname("Jim");
//
//        Customer savedCustomer = new Customer();
//        savedCustomer.setFirstname(customerDTO.getFirstname());
//        savedCustomer.setLastname(customerDTO.getLastname());
//        savedCustomer.setId(1l);
//
//        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
//
//        //when
//        CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);
//
//        //then
//        assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
//        assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
    }

    @Test
    void deleteById() {
        //given
        Long idToDelete = 2L;

        //when
        service.deleteById(idToDelete);

        //no 'when' because method has void return type

        //then
        verify(repository, times(1)).deleteById(anyLong());
    }
}