package com.licenta.sportsbooking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {

    Location location;

    @BeforeEach
    void setUp() {
        location = new Location();
    }

    @Test
    void testId() {
        Long idValue = 3L;
        location.setId(idValue);
        assertEquals(idValue, location.getId());
    }

    @Test
    void testName() {
        String nameValue = "Test Name";
        location.setName(nameValue);
        assertEquals(nameValue, location.getName());
    }

    @Test
    void testTown() {
        Town testTown = new Town();
        testTown.setName("Test");
        location.setTown(testTown);
        assertEquals(testTown, location.getTown());
    }

    @Test
    void testSports() {
        Sport sport1 = new Sport();
        Sport sport2 = new Sport();
        List<Sport> sports = new ArrayList<>();
        sports.add(sport1);
        sports.add(sport2);
        location.setSports(sports);
        assertEquals(sports, location.getSports());
    }
}