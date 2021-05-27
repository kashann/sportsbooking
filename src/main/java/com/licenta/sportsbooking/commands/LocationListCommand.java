package com.licenta.sportsbooking.commands;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LocationListCommand {
    List<LocationCommand> locations;
}
