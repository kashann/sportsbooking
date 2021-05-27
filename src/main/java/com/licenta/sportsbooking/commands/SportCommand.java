package com.licenta.sportsbooking.commands;

import com.licenta.sportsbooking.model.SportType;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class SportCommand implements Comparable<SportCommand> {

    private Long id;

    @NotBlank
    private SportType name;

    private LocalDate startDate;
    private LocalDate endDate;

    @Min(0)
    @NotNull
    private Double avgCostPerDay;

    @Override
    public int compareTo(SportCommand sport) {
        return this.getAvgCostPerDay().compareTo(sport.getAvgCostPerDay());
    }
}
