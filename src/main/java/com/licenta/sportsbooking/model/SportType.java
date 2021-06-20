package com.licenta.sportsbooking.model;

import java.util.ArrayList;
import java.util.List;

public enum SportType {
    SKI, SKYDIVING, ATV, PARAGLIDING, KITEBOARDING, DOWNHILL, SNOWBOARDING;

    public static List<String> getAllNames() {
        List<String> result = new ArrayList<>();
        for (SportType sport : values()) {
            result.add(sport.name());
        }
        return result;
    }
}
