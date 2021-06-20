package com.licenta.sportsbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchLocationDTO {

    private String name;
    private List<String> sports;
    private String fromDate;
    private String toDate;

}
