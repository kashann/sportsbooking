package com.licenta.sportsbooking.dto;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDTO {

    private Long id;
    private String locationName;
    private Set<SportDTO> sports;
}
