package com.licenta.sportsbooking.bootstrap;

import com.licenta.sportsbooking.model.*;
import com.licenta.sportsbooking.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final LocationRepository locationRepository;
    private final SportRepository sportRepository;
    private final TownRepository townRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.debug("Loading Bootstrap Data");

        List<Location> locations = new ArrayList<>();
        List<Country> countries = new ArrayList<>();

        //sports are initialized through data.sql

        //Get sports
        Optional<Sport> skiOptional = sportRepository.findById(1L);
        if (skiOptional.isEmpty()) {
            throw new RuntimeException("Expected Sport not found!");
        }
        Optional<Sport> paraglidingOptional = sportRepository.findById(2L);
        if (paraglidingOptional.isEmpty()) {
            throw new RuntimeException("Expected Sport not found!");
        }
        Optional<Sport> atvOptional = sportRepository.findById(3L);
        if (atvOptional.isEmpty()) {
            throw new RuntimeException("Expected Sport not found!");
        }
        Optional<Sport> downhillOptional = sportRepository.findById(4L);
        if (downhillOptional.isEmpty()) {
            throw new RuntimeException("Expected Sport not found!");
        }
        Optional<Sport> snowboardingOptional = sportRepository.findById(5L);
        if (snowboardingOptional.isEmpty()) {
            throw new RuntimeException("Expected Sport not found!");
        }
        Optional<Sport> auSkiOptional = sportRepository.findById(6L);
        if (auSkiOptional.isEmpty()) {
            throw new RuntimeException("Expected Sport not found!");
        }
        Optional<Sport> auSnowboardingOptional = sportRepository.findById(7L);
        if (auSnowboardingOptional.isEmpty()) {
            throw new RuntimeException("Expected Sport not found!");
        }

        Sport ski = skiOptional.get();
        Sport paragliding = paraglidingOptional.get();
        Sport atv = atvOptional.get();
        Sport downhill = downhillOptional.get();
        Sport snowboarding = snowboardingOptional.get();
        Sport auSki = auSkiOptional.get();
        Sport auSnowboarding = auSnowboardingOptional.get();

        Country romania = new Country("Romania");
        Country austria = new Country("Austria");

        Region brasov = new Region("Brasov");
        Region salzburg = new Region("Salzburg");

        Town predeal = new Town("Predeal");
        Town sacele = new Town("Sacele");
        Town badhofgastein = new Town("Bad Hofgastein");

        brasov.setCountry(romania);
        predeal.setRegion(brasov);
        sacele.setRegion(brasov);

        salzburg.setCountry(austria);
        badhofgastein.setRegion(salzburg);

        Location slope = new Location();
        slope.setName("Predeal slope");
        slope.setTown(predeal);
        ski.setLocation(slope);
        atv.setLocation(slope);
        snowboarding.setLocation(slope);
        locations.add(slope);

        Location bunloc = new Location();
        bunloc.setName("Bunloc");
        bunloc.setTown(sacele);
        paragliding.setLocation(bunloc);
        downhill.setLocation(bunloc);
        locations.add(bunloc);

        Location angertal = new Location();
        angertal.setName("Angertal");
        angertal.setTown(badhofgastein);
        auSki.setLocation(angertal);
        auSnowboarding.setLocation(angertal);
        locations.add(angertal);

        countries.add(romania);
        countries.add(austria);

        locationRepository.saveAll(locations);
        countryRepository.saveAll(countries);
    }

}
