package com.licenta.sportsbooking.controllers.mvc;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.SearchLocationRequest;
import com.licenta.sportsbooking.dto.SearchResultDTO;
import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.model.Location;
import com.licenta.sportsbooking.model.SportType;
import com.licenta.sportsbooking.services.LocationServiceImpl;
import com.licenta.sportsbooking.utils.LangMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final static String VIEWS_LOCATION_DETAILS = "locations/locationDetails";
    private final static String VIEWS_LOCATION_LIST    = "locations/locationsList";
    private final static String VIEWS_LOCATION_SEARCH  = "locations/searchLocations";
    private final static String VIEWS_LOCATION_CREATE_OR_UPDATE_FORM = "locations/addOrEditLocation";

    private final LocationServiceImpl locationService;

    @GetMapping("/search")
    public ModelAndView searchLocation() {
        ModelAndView mav = new ModelAndView(VIEWS_LOCATION_SEARCH);
        List<String> sports = SportType.getAllNames();
        mav.addObject("sports", sports);
        mav.addObject("searchLocation", new SearchLocationRequest());
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView seeLocation(@PathVariable("id") Long locationId) {
        ModelAndView mav = new ModelAndView(VIEWS_LOCATION_DETAILS);
        LocationDTO location = locationService.findById(locationId);
        mav.addObject("location", location);
        return mav;
    }

    @GetMapping
    public String processFindForm(SearchLocationRequest searchLocation, Model model) {
        List<SearchResultDTO> results = locationService.searchLocations(searchLocation);

        if (results.isEmpty()) {
            // no locations found
            List<String> sports = SportType.getAllNames();
            model.addAttribute("sports", sports);
            model.addAttribute("result", LangMsg.get("location.search.notFound"));
            model.addAttribute("searchLocation", searchLocation);
            return VIEWS_LOCATION_SEARCH;
        } else if (results.size() == 1) {
            // 1 location found
            return "redirect:/locations/" + results.get(0).getId();
        } else {
            // multiple locations found
            model.addAttribute("selections", results);
            return VIEWS_LOCATION_LIST;
        }
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        List<String> sports = SportType.getAllNames();
        model.addAttribute("sportValues", sports);
        model.addAttribute("location", new LocationDTO());
        return VIEWS_LOCATION_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid LocationDTO location, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_LOCATION_CREATE_OR_UPDATE_FORM;
        } else {
            LocationDTO savedLocation = locationService.saveLocation(location);
            return "redirect:/locations/" + savedLocation.getId();
        }
    }

    @GetMapping("/{id}/edit")
    public String initUpdateOwnerForm(@PathVariable("id") Long locationId, Model model) {
        List<String> sports = SportType.getAllNames();
        model.addAttribute("sportValues", sports);
        model.addAttribute("location", locationService.findById(locationId));
        return VIEWS_LOCATION_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processUpdateOwnerForm(@Valid LocationDTO location, BindingResult result, @PathVariable("id") Long locationId) {
        if (result.hasErrors()) {
            return VIEWS_LOCATION_CREATE_OR_UPDATE_FORM;
        } else {
            LocationDTO savedLocation = locationService.saveLocation(location);
            return "redirect:/locations/" + savedLocation.getId();
        }
    }

}