package com.licenta.sportsbooking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.licenta.sportsbooking.controllers.rest.LocationRestController;
import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.dto.TownDTO;
import com.licenta.sportsbooking.model.SportType;
import com.licenta.sportsbooking.services.LocationServiceImpl;
import com.licenta.sportsbooking.services.SportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class LocationRestControllerTest {

    LocationRestController controller;

    @Mock
    LocationServiceImpl service;

    @Mock
    SportServiceImpl sportService;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new LocationRestController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new NotFoundControllerAdvice())
                .build();
    }

    @Test
    void getAllLocations() throws Exception {
        LocationDTO location1 = new LocationDTO();
        location1.setId(1L);
        location1.setName("Test1");

        LocationDTO location2 = new LocationDTO();
        location2.setId(2L);
        location2.setName("Test2");

        List<LocationDTO> locations = new ArrayList<>();
        locations.add(location1);
        locations.add(location2);

        when(service.getLocations()).thenReturn(locations);

        mockMvc.perform(get("/api/locations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":1,\"name\":\"Test1\"}, {\"id\":2,\"name\":\"Test2\"}]"));
    }

    @Test
    void getLocationByID() throws Exception {
        LocationDTO location = new LocationDTO();
        location.setId(1L);
        location.setName("Test");

        when(service.findById(anyLong())).thenReturn(location);

        mockMvc.perform(get("/api/locations/" + location.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"name\":\"Test\"}"));
    }

    @Test
    void addLocation() throws Exception {
        LocationDTO location = new LocationDTO();
        location.setId(2L);
        location.setName("POST Test");

        when(service.saveLocation(location, location.getTown().getId())).thenReturn(location);

        mockMvc.perform(post("/api/locations/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":2,\"name\":\"POST Test\"}"));
    }

    @Test
    void modifyLocation() throws Exception {
        Long locationId = 2L;
        LocationDTO location = new LocationDTO();
        location.setId(locationId);
        location.setName("PUT Test");

        mockMvc.perform(post("/api/locations/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isCreated());

        TownDTO town = new TownDTO();
        town.setName("Town");
        location.setTown(town);
        SportDTO sport1 = new SportDTO();
        sport1.setName(SportType.SKI.name());
        sport1.setAvgCostPerDay(150.0);
        sport1.setStartDate(LocalDate.of(2020,12,15));
        sport1.setEndDate(LocalDate.of(2021, 4, 15));
        SportDTO sport2 = new SportDTO();
        sport2.setName(SportType.SNOWBOARDING.name());
        sport2.setAvgCostPerDay(130.0);
        sport2.setStartDate(LocalDate.of(2020,12,15));
        sport2.setEndDate(LocalDate.of(2021, 4, 15));
        location.getSports().add(sport1);
        location.getSports().add(sport2);

        mockMvc.perform(put("/api/locations/" + locationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isOk());

        when(service.findById(anyLong())).thenReturn(location);

        LocationDTO updatedLocation = service.findById(locationId);
        assertEquals(town.getName(), updatedLocation.getTown().getName());
        assertEquals(location.getSports().size(), updatedLocation.getSports().size());
    }

    @Test
    void deleteLocation() throws Exception {
        Long idToDelete = 1L;
        mockMvc.perform(delete("/api/locations/" + idToDelete))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteById(idToDelete);
    }

    @Test
    void searchLocationsTest() throws Exception {
        LocationDTO location = new LocationDTO();
        location.setId(1L);
        location.setName("Bunloc");
        List<SportDTO> sportDTOS = new ArrayList<>();
        SportDTO dh = new SportDTO();
        dh.setName(SportType.DOWNHILL.name());
        dh.setAvgCostPerDay(20.0);
        dh.setStartDate(LocalDate.of(2020, 4, 1));
        dh.setEndDate(LocalDate.of(2020, 11, 1));
        sportDTOS.add(dh);
        location.setSports(sportDTOS);
        ArrayList<LocationDTO> allLocations = new ArrayList<>();
        allLocations.add(location);

        when(service.getLocations()).thenReturn(allLocations);
        when(sportService.findSportsByLocationNameAndPeriod(any(LocationDTO.class), any(), any(), any()))
                .thenReturn(sportDTOS);

        mockMvc.perform(get("/api/locations/search?sports=" + dh.getName() +
                "&from=2020-06-01&to=2020-06-10&sort=ASC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}