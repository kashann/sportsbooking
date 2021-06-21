package com.licenta.sportsbooking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "locations")
@ToString(exclude = "locations")
@Entity
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "town")
    private List<Location> locations = new ArrayList<>();

    public Town(String name) {
        this.name = name;
    }

    public void setRegion(Region region) {
        region.getTowns().add(this);
        this.region = region;
    }

    public void addLocation(Location location) {
        //prevent endless loop
        if (locations.contains(location)) {
            return;
        }
        //add new location
        locations.add(location);
        //set location into the location
        location.setTown(this);
    }

    public void removeLocation(Location location) {
        //prevent endless loop
        if (!locations.contains(location)) {
            return;
        }
        //remove the locations
        locations.remove(location);
        //remove location into the location
        location.setTown(null);
    }
}
