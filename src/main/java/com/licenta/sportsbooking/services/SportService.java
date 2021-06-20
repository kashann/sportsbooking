package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.SportDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface SportService {

    Set<SportDTO> findSportsByLocationNameAndPeriod(LocationDTO location, List<String> sportNames,
                                                    LocalDate from, LocalDate to);
    Set<SportDTO> findSportsByLocationAndName(LocationDTO location, List<String> sportNames);
    Set<SportDTO> findSportsByLocation(LocationDTO location);
}
