package com.licenta.sportsbooking.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationListDTO {

    List<LocationDTO> locations;
}
