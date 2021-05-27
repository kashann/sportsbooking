package com.licenta.sportsbooking.controllers;

import com.licenta.sportsbooking.commands.LocationCommand;
import com.licenta.sportsbooking.commands.LocationListCommand;
import com.licenta.sportsbooking.commands.SearchResultDTO;
import com.licenta.sportsbooking.commands.SportCommand;
import com.licenta.sportsbooking.model.SportType;
import com.licenta.sportsbooking.services.LocationServiceImpl;
import com.licenta.sportsbooking.services.SportServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(LocationController.BASE_URL)
public class LocationController {

    public static final String BASE_URL = "/api/locations";

    private final LocationServiceImpl locationService;
    private final SportServiceImpl sportService;

    public LocationController(LocationServiceImpl locationService, SportServiceImpl sportService) {
        this.locationService = locationService;
        this.sportService = sportService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public LocationListCommand getAllLocations() {
        return new LocationListCommand(locationService.getLocations());
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public LocationCommand getLocationByID(@PathVariable Long id){
        return locationService.findById(id);
    }

    @PostMapping("new")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationCommand addLocation(@Valid @RequestBody LocationCommand location) {
        return locationService.saveLocationCommand(location);
    }

    @PutMapping("{id}")
    public LocationCommand modifyLocation(@Valid @RequestBody LocationCommand newLocation, @PathVariable Long id) {
        LocationCommand command = locationService.findById(id);
        if(command != null) {
            command.setName(newLocation.getName());
            command.setTown(newLocation.getTown());
            command.setSports(newLocation.getSports());
            return locationService.saveLocationCommand(command);
        }
        else {
            newLocation.setId(id);
            return locationService.saveLocationCommand(newLocation);
        }
    }

    @DeleteMapping("{id}")
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
        List<SportType> sportTypes = new ArrayList<>();
        sports.forEach(sport -> {
            try {
                sportTypes.add(SportType.valueOf(sport));
            } catch(Exception e) {
                log.error(e.getMessage());
            }
        });
        List<SearchResultDTO> locationsSearchResults = new ArrayList<>();
        List<LocationCommand> searchResults = new ArrayList<>(locationService.getLocations());
        searchResults.forEach(locationCommand -> {
            Set<SportCommand> sportSearchResults = sportService.findSportsByLocationNameAndPeriod(locationCommand, sportTypes, from, to);
            if(sportSearchResults.size() > 0) {
                SearchResultDTO resultDTO = new SearchResultDTO(locationCommand.getName(), sportSearchResults);
                locationsSearchResults.add(resultDTO);
            }
        });

        if(sort != null) {
            //sorting by the average of all sports at that location
            locationsSearchResults.sort((o1, o2) -> {
                List<SportCommand> s1 = new ArrayList<>(o1.getSports());
                List<SportCommand> s2 = new ArrayList<>(o2.getSports());
                Double avg1 = getAverage(s1);
                Double avg2 = getAverage(s2);

                if(sort.equals("ASC")) {
                    if(avg1 > avg2)
                        return 1;
                    else if(avg1 < avg2)
                        return -1;
                }
                else if(sort.equals("DESC")) {
                    if(avg1 > avg2)
                        return -1;
                    else if(avg1 < avg2)
                        return 1;
                }
                return 0;
            });
        }

        return locationsSearchResults;
    }

    private Double getAverage(List<SportCommand> sports) {
        Double sum = 0.0;
        for(int i = 0; i < sports.size(); i++) {
            sum += sports.get(i).getAvgCostPerDay();
        }
        sum /= sports.size();
        return sum;
    }
}
