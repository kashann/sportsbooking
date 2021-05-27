package com.licenta.sportsbooking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.licenta.sportsbooking.commands.LocationCommand;
import com.licenta.sportsbooking.commands.SportCommand;
import com.licenta.sportsbooking.commands.TownCommand;
import com.licenta.sportsbooking.model.SportType;
import com.licenta.sportsbooking.services.LocationServiceImpl;
import com.licenta.sportsbooking.services.SportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LocationControllerTest {

    LocationController controller;

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

        controller = new LocationController(service, sportService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new NotFoundControllerAdvice())
                .build();
    }

    @Test
    void getAllLocations() throws Exception {
        LocationCommand location1 = new LocationCommand();
        location1.setId(1L);
        location1.setName("Test1");

        LocationCommand location2 = new LocationCommand();
        location2.setId(2L);
        location2.setName("Test2");

        List<LocationCommand> locations = new ArrayList<>();
        locations.add(location1);
        locations.add(location2);

        when(service.getLocations()).thenReturn(locations);

        mockMvc.perform(get("/api/locations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"locations\":[{\"id\":1,\"name\":\"Test1\"}, {\"id\":2,\"name\":\"Test2\"}]}"));
    }

    @Test
    void getLocationByID() throws Exception {
        LocationCommand location = new LocationCommand();
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
        LocationCommand location = new LocationCommand();
        location.setId(2L);
        location.setName("POST Test");

        when(service.saveLocationCommand(location)).thenReturn(location);

        //todo fix nullPointerException. POST working, mock not
        mockMvc.perform(post("/api/locations/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":2,\"name\":\"POST Test\"}"));
    }

    @Test
    void modifyLocation() throws Exception {
        LocationCommand location = new LocationCommand();
        location.setId(2L);
        location.setName("PUT Test");
        TownCommand town = new TownCommand();
        town.setName("Town");
        location.setTown(town);
        SportCommand sport1 = new SportCommand();
        sport1.setName(SportType.SKI);
        sport1.setAvgCostPerDay(150.0);
        sport1.setStartDate(LocalDate.of(2020,12,15));
        sport1.setEndDate(LocalDate.of(2021, 4, 15));
        SportCommand sport2 = new SportCommand();
        sport2.setName(SportType.SNOWBOARDING);
        sport2.setAvgCostPerDay(130.0);
        sport2.setStartDate(LocalDate.of(2020,12,15));
        sport2.setEndDate(LocalDate.of(2021, 4, 15));
        location.getSports().add(sport1);
        location.getSports().add(sport2);


        when(service.saveLocationCommand(location)).thenReturn(location);

        //todo fix nullPointerException. PUT working, mock not
        mockMvc.perform(put("/api/locations/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":2,\"name\":\"PUT Test\",\"town\":{\"id\":1,\"name\":\"Town\"},\"sports\":[{\"name\":\"SKI\",\"startDate\":[2020,12,15],\"endDate\":[2021,4,15],\"avgCostPerDay\":150},{\"name\":\"SNOWBOARDING\",\"startDate\":[2020,12,15],\"endDate\":[2021,4,15],\"avgCostPerDay\":130}]}"));
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
        LocationCommand location = new LocationCommand();
        location.setId(1L);
        location.setName("Bunloc");
        Set<SportCommand> sportCommands = new HashSet<>();
        SportCommand dh = new SportCommand();
        dh.setName(SportType.DOWNHILL);
        dh.setAvgCostPerDay(20.0);
        dh.setStartDate(LocalDate.of(2020, 4, 1));
        dh.setEndDate(LocalDate.of(2020, 11, 1));
        sportCommands.add(dh);
        location.setSports(sportCommands);
        ArrayList<LocationCommand> allLocations = new ArrayList<>();
        allLocations.add(location);

        when(service.getLocations()).thenReturn(allLocations);
        when(sportService.findSportsByLocationNameAndPeriod(any(LocationCommand.class), any(), any(), any()))
                .thenReturn(sportCommands);

        //todo further testing on json response. Requires additional dependency
        mockMvc.perform(get("/api/locations/search?sports=" + dh.getName() +
                "&from=2020-06-01&to=2020-06-10&sort=ASC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//                .andExpect(content().json("{\"locationName\":\"Bunloc\",\"sports\":[{\"id\":1,\"name\":\"DOWNHILL\",\"startDate\":[2020,4,1],\"endDate\":[2020,11,1],\"avgCostPerDay\":20}]}"));
    }
}