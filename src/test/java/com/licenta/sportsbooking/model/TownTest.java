package com.licenta.sportsbooking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TownTest {

    Town town;

    @BeforeEach
    void setUp() {
        town = new Town();
    }

    @Test
    void testId() {
        Long idValue = 4L;
        town.setId(idValue);
        assertEquals(idValue, town.getId());
    }

    @Test
    void testName() {
        String nameValue = "Test Name";
        town.setName(nameValue);
        assertEquals(nameValue, town.getName());
    }

    @Test
    void testRegion() {
        Region testRegion = new Region();
        testRegion.setName("Test");
        town.setRegion(testRegion);
        assertEquals(testRegion, town.getRegion());
    }
}