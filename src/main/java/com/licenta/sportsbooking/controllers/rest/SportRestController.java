package com.licenta.sportsbooking.controllers.rest;

import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.services.SportServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SportRestController {

    private final SportServiceImpl sportService;

    @GetMapping("/locations/{locationId}/sports/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SportDTO getSportByID(@PathVariable Long id) {
        return sportService.findById(id);
    }

    @PostMapping("/locations/{locationId}/sports/new")
    @ResponseStatus(HttpStatus.CREATED)
    public SportDTO addSport(@Valid @RequestBody SportDTO sport, @PathVariable Long locationId) {
        return sportService.saveSport(sport, locationId);
    }

    @PutMapping("/locations/{locationId}/sports/{id}")
    public SportDTO modifySport(@Valid @RequestBody SportDTO newSport, @PathVariable Long locationId, @PathVariable Long id) {
        return sportService.modifySport(newSport, locationId);
    }

    @DeleteMapping("/locations/{locationId}/sports/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSport(@PathVariable Long id) {
        sportService.deleteById(id);
    }
}
