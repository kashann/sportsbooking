package com.licenta.sportsbooking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "towns")
@Entity
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "region")
    private Set<Town> towns = new HashSet<>();

    public Region(String name) {
        this.name = name;
    }

    public void setCountry(Country country) {
        country.getRegions().add(this);
        this.country = country;
    }
}
