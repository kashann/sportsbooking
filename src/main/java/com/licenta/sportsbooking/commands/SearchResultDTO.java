package com.licenta.sportsbooking.commands;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SearchResultDTO {

    private String locationName;
    private Set<SportCommand> sports;
}
