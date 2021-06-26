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
        Optional<Sport> tntSkydivingOptional = sportRepository.findById(8L);
        if (tntSkydivingOptional.isEmpty()) {
            throw new RuntimeException("Expected Sport not found!");
        }
        Optional<Sport> tntParaglidingOptional = sportRepository.findById(9L);
        if (tntParaglidingOptional.isEmpty()) {
            throw new RuntimeException("Expected Sport not found!");
        }
        Optional<Sport> kiteboardingOptional = sportRepository.findById(10L);
        if (kiteboardingOptional.isEmpty()) {
            throw new RuntimeException("Expected Sport not found!");
        }

        Sport ski = skiOptional.get();
        Sport paragliding = paraglidingOptional.get();
        Sport atv = atvOptional.get();
        Sport downhill = downhillOptional.get();
        Sport snowboarding = snowboardingOptional.get();
        Sport auSki = auSkiOptional.get();
        Sport auSnowboarding = auSnowboardingOptional.get();
        Sport tntSkydiving = tntSkydivingOptional.get();
        Sport tntParagliding = tntParaglidingOptional.get();
        Sport kiteboarding = kiteboardingOptional.get();

        Country romania = new Country("Romania");
        Country austria = new Country("Austria");

        Region brasov = new Region("Brasov");
        Region constanta = new Region("Constanta");
        Region ilfov = new Region("Ilfov");
        Region salzburg = new Region("Salzburg");

        Town predeal = new Town("Predeal");
        Town mamaia = new Town("Mamaia");
        Town clinceni = new Town("Clinceni");
        Town sacele = new Town("Sacele");
        Town badhofgastein = new Town("Bad Hofgastein");

        brasov.setCountry(romania);
        constanta.setCountry(romania);
        mamaia.setRegion(constanta);
        ilfov.setCountry(romania);
        clinceni.setRegion(ilfov);
        predeal.setRegion(brasov);
        sacele.setRegion(brasov);

        salzburg.setCountry(austria);
        badhofgastein.setRegion(salzburg);

        Location susai = new Location();
        susai.setName("Cabana Susai");
        susai.setTown(predeal);
        ski.setLocation(susai);
        atv.setLocation(susai);
        snowboarding.setLocation(susai);
        locations.add(susai);

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

        Location tntBrothers = new Location();
        tntBrothers.setName("TNT Brothers Skydiving");
        tntBrothers.setTown(clinceni);
        tntSkydiving.setLocation(tntBrothers);
        tntParagliding.setLocation(tntBrothers);
        locations.add(tntBrothers);

        Location blueBeach = new Location();
        blueBeach.setName("Blue Beach");
        blueBeach.setTown(mamaia);
        kiteboarding.setLocation(blueBeach);
        locations.add(blueBeach);

        countries.add(romania);
        countries.add(austria);

        locationRepository.saveAll(locations);
        countryRepository.saveAll(countries);
    }

}
