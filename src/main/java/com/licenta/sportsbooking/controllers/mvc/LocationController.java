package com.licenta.sportsbooking.controllers.mvc;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.SportType;
import com.licenta.sportsbooking.services.LocationServiceImpl;
import com.licenta.sportsbooking.services.SportServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final static String VIEWS_LOCATION_DETAILS = "locations/locationDetails";
    private final static String VIEWS_SEARCH_LOCATIONS = "locations/searchLocations";

    private final LocationServiceImpl locationService;

    @RequestMapping({"/", ""})
    public ModelAndView searchLocation() {
        ModelAndView mav = new ModelAndView(VIEWS_SEARCH_LOCATIONS);
        List<SportType> sports = Arrays.asList(SportType.values());
        mav.addObject("sports", sports);
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView seeLocation(@PathVariable("id") Long locationId) {
        ModelAndView mav = new ModelAndView(VIEWS_LOCATION_DETAILS);
        LocationDTO location = locationService.findById(locationId);
        mav.addObject("location", location);
        return mav;
    }

}