package com.licenta.sportsbooking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "sports")
@ToString(exclude = "sports")
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "town_id")
    private Town town;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "location")
    private List<Sport> sports = new ArrayList<>();

    public void setTown(Town town) {
        //prevent endless loop
        if (this.town != null && this.town.equals(town)) {
            return;
        }
        //set new town
        Town oldTown = this.town;
        this.town = town;
        //remove from the old town
        if (oldTown != null) {
            oldTown.removeLocation(this);
        }
        //set sport into new town
        if (town != null) {
            town.addLocation(this);
        }
    }

    public void addSport(Sport sport) {
        //prevent endless loop
        if (sports.contains(sport)) {
            return;
        }
        //add new sport
        sports.add(sport);
        //set location into the sport
        sport.setLocation(this);
    }

    public void removeSport(Sport sport) {
        //prevent endless loop
        if (!sports.contains(sport)) {
            return;
        }
        //remove the sports
        sports.remove(sport);
        //remove location into the sport
        sport.setLocation(null);
    }
}
