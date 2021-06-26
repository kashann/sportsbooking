package com.licenta.sportsbooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SportDTO implements Comparable<SportDTO> {

    private Long id;

    @NotBlank
    private String name;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate endDate;

    @Min(0)
    @NotNull
    private Double avgCostPerDay;

    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public int compareTo(SportDTO sport) {
        return this.getAvgCostPerDay().compareTo(sport.getAvgCostPerDay());
    }
}
