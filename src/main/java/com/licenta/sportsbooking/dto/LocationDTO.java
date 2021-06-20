package com.licenta.sportsbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String name;
    private TownDTO town;
    private Set<SportDTO> sports = new HashSet<>();
}
