package com.licenta.sportsbooking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegionTest {
    
    Region region;

    @BeforeEach
    void setUp() {
        region = new Region();
    }

    @Test
    void testId() {
        Long idValue = 5L;
        region.setId(idValue);
        assertEquals(idValue, region.getId());
    }

    @Test
    void testName() {
        String nameValue = "Test Name";
        region.setName(nameValue);
        assertEquals(nameValue, region.getName());
    }

    @Test
    void testCountry() {
        Country testCountry = new Country();
        testCountry.setName("Test");
        region.setCountry(testCountry);
        assertEquals(testCountry, region.getCountry());
    }

    @Test
    void testTowns() {
        Town town1 = new Town();
        Town town2 = new Town();
        Set<Town> towns = new HashSet<>();
        towns.add(town1);
        towns.add(town2);
        region.setTowns(towns);
        assertEquals(towns, region.getTowns());
    }
}