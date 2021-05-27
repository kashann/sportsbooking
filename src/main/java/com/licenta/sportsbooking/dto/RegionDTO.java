package com.licenta.sportsbooking.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String name;
}
