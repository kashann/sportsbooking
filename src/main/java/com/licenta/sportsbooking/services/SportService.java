package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.dto.LocationDTO;
import com.licenta.sportsbooking.dto.SportDTO;
import com.licenta.sportsbooking.model.SportType;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface SportService {

    Set<SportDTO> findSportsByLocationNameAndPeriod(LocationDTO location, List<SportType> names,
                                                    LocalDate from, LocalDate to);
}
