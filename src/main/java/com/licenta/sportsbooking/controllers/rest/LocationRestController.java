package com.licenta.sportsbooking.controllers.rest;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.SearchResultDTO;
import com.licenta.sportsbooking.services.LocationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationRestController {

    private final LocationServiceImpl locationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LocationDTO> getAllLocations() {
        return locationService.getLocations();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LocationDTO getLocationByID(@PathVariable Long id) {
        return locationService.findById(id);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDTO addLocation(@Valid @RequestBody LocationDTO location) {
        return locationService.saveLocation(location, location.getTown().getId());
    }

    @PutMapping("/{id}")
    public LocationDTO modifyLocation(@Valid @RequestBody LocationDTO newLocation, @PathVariable Long id) {
        return locationService.modifyLocation(newLocation, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLocation(@PathVariable Long id) {
        locationService.deleteById(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<SearchResultDTO> searchLocations(@RequestParam List<String> sports,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                 @RequestParam(required = false) String sort) {
        return locationService.searchLocations(sports, from, to, sort);
    }

}
