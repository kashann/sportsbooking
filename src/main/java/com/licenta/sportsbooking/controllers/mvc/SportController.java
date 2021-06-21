package com.licenta.sportsbooking.controllers.mvc;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.model.SportType;
import com.licenta.sportsbooking.services.SportServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SportController {

    private final static String VIEWS_SPORT_CREATE_OR_UPDATE_FORM = "sports/addOrEditSport";

    private final SportServiceImpl sportService;

    @GetMapping("/locations/{locationId}/sports/new")
    public String initCreationForm(Model model) {
        List<String> sports = SportType.getAllNames();
        model.addAttribute("sportValues", sports);
        model.addAttribute("sport", new SportDTO());
        return VIEWS_SPORT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/locations/{locationId}/sports/new")
    public String processCreationForm(@PathVariable("locationId") Long locationId, @Valid SportDTO sport, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_SPORT_CREATE_OR_UPDATE_FORM;
        } else {
            sportService.saveSport(sport, locationId);
            return "redirect:/locations/" + locationId + "/edit";
        }
    }

    @GetMapping("/locations/{locationId}/sports/{id}/edit")
    public String initUpdateSportForm(@PathVariable("id") Long sportId, Model model) {
        List<String> sports = SportType.getAllNames();
        model.addAttribute("sportValues", sports);
        model.addAttribute("sport", sportService.findById(sportId));
        return VIEWS_SPORT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/locations/{locationId}/sports/{id}/edit")
    public String processUpdateSportForm(@PathVariable("locationId") Long locationId, @Valid SportDTO sport, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_SPORT_CREATE_OR_UPDATE_FORM;
        } else {
            sportService.modifySport(sport, locationId);
            return "redirect:/locations/" + locationId + "/edit";
        }
    }

    @GetMapping("/locations/{locationId}/sports/{id}/delete")
    public String processDeleteSport(@PathVariable("locationId") Long locationId, @PathVariable("id") Long sportId) {
        sportService.deleteById(sportId);
        return "redirect:/locations/" + locationId + "/edit";
    }
}
