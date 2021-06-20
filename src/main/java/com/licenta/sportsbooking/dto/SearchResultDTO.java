package com.licenta.sportsbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDTO {

    private Long id;
    private String locationName;
    private String townName;
    private Set<SportDTO> sports;
}
