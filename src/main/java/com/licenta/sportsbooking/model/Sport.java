package com.licenta.sportsbooking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

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

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public void setLocation(Location location) {
        //prevent endless loop
        if (this.location != null && this.location.equals(location)) {
            return;
        }
        //set new location
        Location oldLocation = this.location;
        this.location = location;
        //remove from the old location
        if (oldLocation != null) {
            oldLocation.removeSport(this);
        }
        //set sport into new location
        if (location != null) {
            location.addSport(this);
        }
    }

    @Override
    public int compareTo(Sport sport) {
        return this.getAvgCostPerDay().compareTo(sport.getAvgCostPerDay());
    }
}
