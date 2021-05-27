package com.licenta.sportsbooking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "location")
@ToString(exclude = "location")
@Entity
public class Sport implements Comparable<Sport> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private SportType name;

    private LocalDate startDate;
    private LocalDate endDate;
    private Double avgCostPerDay;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    public void setLocation(Location location) {
        location.getSports().add(this);
        this.location = location;
    }

    @Override
    public int compareTo(Sport sport) {
        return this.getAvgCostPerDay().compareTo(sport.getAvgCostPerDay());
    }
}
