package com.licenta.sportsbooking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id")
    private Region region;

    public Town(String name) {
        this.name = name;
    }

    public void setRegion(Region region) {
        region.getTowns().add(this);
        this.region = region;
    }
}
