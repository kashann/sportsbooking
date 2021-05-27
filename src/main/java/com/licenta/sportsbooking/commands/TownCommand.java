package com.licenta.sportsbooking.commands;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TownCommand {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String name;
}
