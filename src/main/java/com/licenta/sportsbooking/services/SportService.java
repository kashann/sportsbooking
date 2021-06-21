package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.SportDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface SportService {

    SportDTO saveSport(SportDTO sport, Long locationId);
    SportDTO modifySport(SportDTO sport, Long locationId);
    SportDTO findById(Long id);
    List<SportDTO> findSportsByLocationNameAndPeriod(LocationDTO location, List<String> sportNames, LocalDate from, LocalDate to);
    List<SportDTO> findSportsByLocationAndName(LocationDTO location, List<String> sportNames);
    List<SportDTO> findSportsByLocation(LocationDTO location);
}
