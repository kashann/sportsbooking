package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.commands.LocationCommand;
import com.licenta.sportsbooking.commands.SportCommand;
import com.licenta.sportsbooking.model.SportType;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface SportService {

    Set<SportCommand> findSportsByLocationNameAndPeriod(LocationCommand location, List<SportType> names,
                                                        LocalDate from, LocalDate to);
}
