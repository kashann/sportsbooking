package com.licenta.sportsbooking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryTest {

    Country country;

    @BeforeEach
    void setUp() {
        country = new Country();
    }

    @Test
    void testId() {
        Long idValue = 6L;
        country.setId(idValue);
        assertEquals(idValue, country.getId());
    }

    @Test
    void testName() {
        String nameValue = "Test Name";
        country.setName(nameValue);
        assertEquals(nameValue, country.getName());
    }

    @Test
    void getRegions() {
        Region region1 = new Region();
        Region region2 = new Region();
        Set<Region> regions = new HashSet<>();
        regions.add(region1);
        regions.add(region2);
        country.setRegions(regions);
        assertEquals(regions, country.getRegions());
    }
}