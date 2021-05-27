package com.licenta.sportsbooking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SportTest {

    Sport sport;

    @BeforeEach
    void setUp() {
        sport = new Sport();
    }

    @Test
    void testId() {
        Long idValue = 2L;
        sport.setId(idValue);
        assertEquals(idValue, sport.getId());
    }

    @Test
    void testName() {
        SportType nameValue = SportType.DOWNHILL;
        sport.setName(nameValue);
        assertEquals(nameValue, sport.getName());
    }

    @Test
    void testStartDate() {
        LocalDate testDate = LocalDate.now();
        sport.setStartDate(testDate);
        assertEquals(testDate, sport.getStartDate());
    }

    @Test
    void testEndDate() {
        LocalDate testDate = LocalDate.now();
        sport.setEndDate(testDate);
        assertEquals(testDate, sport.getEndDate());
    }

    @Test
    void testAvgCostPerDay() {
        Double testCost = 20.0;
        sport.setAvgCostPerDay(testCost);
        assertEquals(testCost, sport.getAvgCostPerDay());
    }

    @Test
    void testLocation() {
        Location location = new Location();
        location.setName("Test");
        sport.setLocation(location);
        assertEquals(location, sport.getLocation());
    }
}