package com.licenta.sportsbooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String name;
    private TownDTO town;
    private List<SportDTO> sports = new ArrayList<>();

    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }
}
