package com.licenta.sportsbooking.commands;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
public class LocationCommand {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String name;
    private TownCommand town;
    private Set<SportCommand> sports = new HashSet<>();
}
