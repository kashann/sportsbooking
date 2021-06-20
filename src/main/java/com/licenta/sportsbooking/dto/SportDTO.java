package com.licenta.sportsbooking.dto;

import com.licenta.sportsbooking.model.SportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private SportType name;

    private LocalDate startDate;
    private LocalDate endDate;

    @Min(0)
    @NotNull
    private Double avgCostPerDay;



    @Override
    public int compareTo(SportDTO sport) {
        return this.getAvgCostPerDay().compareTo(sport.getAvgCostPerDay());
    }
}
