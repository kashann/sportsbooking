package com.licenta.sportsbooking.controllers.rest;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.LocationListDTO;
import com.licenta.sportsbooking.dto.SearchResultDTO;
import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.model.SportType;
import com.licenta.sportsbooking.services.LocationServiceImpl;
import com.licenta.sportsbooking.services.SportServiceImpl;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationRestController {

    private final LocationServiceImpl locationService;
    private final SportServiceImpl sportService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public LocationListDTO getAllLocations() {
        return new LocationListDTO(locationService.getLocations());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LocationDTO getLocationByID(@PathVariable Long id) {
        return locationService.findById(id);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDTO addLocation(@Valid @RequestBody LocationDTO location) {
        return locationService.saveLocation(location);
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
        List<SportType> sportTypes = new ArrayList<>();
        sports.forEach(sport -> {
            try {
                sportTypes.add(SportType.valueOf(sport));
            } catch(Exception e) {
                log.error(e.getMessage());
            }
        });
        List<SearchResultDTO> locationsSearchResults = new ArrayList<>();
        List<LocationDTO> searchResults = new ArrayList<>(locationService.getLocations());
        searchResults.forEach(locationDTO -> {
            Set<SportDTO> sportSearchResults = sportService.findSportsByLocationNameAndPeriod(locationDTO, sportTypes, from, to);
            if (sportSearchResults.size() > 0) {
                SearchResultDTO resultDTO = new SearchResultDTO(locationDTO.getName(), sportSearchResults);
                locationsSearchResults.add(resultDTO);
            }
        });

        if (sort != null) {
            //sorting by the average of all sports at that location
            locationsSearchResults.sort((o1, o2) -> {
                List<SportDTO> s1 = new ArrayList<>(o1.getSports());
                List<SportDTO> s2 = new ArrayList<>(o2.getSports());
                Double avg1 = getAverage(s1);
                Double avg2 = getAverage(s2);

                if (sort.equals("ASC")) {
                    if (avg1 > avg2)
                        return 1;
                    else if (avg1 < avg2)
                        return -1;
                }
                else if (sort.equals("DESC")) {
                    if (avg1 > avg2)
                        return -1;
                    else if (avg1 < avg2)
                        return 1;
                }
                return 0;
            });
        }

        return locationsSearchResults;
    }

    private Double getAverage(List<SportDTO> sports) {
        Double sum = 0.0;
        for(int i = 0; i < sports.size(); i++) {
            sum += sports.get(i).getAvgCostPerDay();
        }
        sum /= sports.size();
        return sum;
    }
}
