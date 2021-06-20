package com.licenta.sportsbooking.bootstrap;

import com.licenta.sportsbooking.model.*;
import com.licenta.sportsbooking.repositories.LocationRepository;
import com.licenta.sportsbooking.repositories.SportRepository;
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
public class LocationBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final LocationRepository locationRepository;
    private final SportRepository sportRepository;

    public LocationBootstrap(LocationRepository locationRepository, SportRepository sportRepository) {
        this.locationRepository = locationRepository;
        this.sportRepository = sportRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        locationRepository.saveAll(getLocations());
        log.debug("Loading Bootstrap Data");
    }

    private List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();

        //Get sports
        Optional<Sport> skiOptional = sportRepository.findById(1L);
        if (skiOptional.isEmpty()) {
            throw new RuntimeException("Expected Sport not found!");
        }
        Optional<Sport> paraglidingOptional = sportRepository.findByName(SportType.PARAGLIDING);
        if (paraglidingOptional.isEmpty()) {
            throw new RuntimeException("Expected Sport not found!");
        }
        Optional<Sport> atvOptional = sportRepository.findByName(SportType.ATV);
        if (atvOptional.isEmpty()) {
            throw new RuntimeException("Expected Sport not found!");
        }
        Optional<Sport> downhillOptional = sportRepository.findByName(SportType.DOWNHILL);
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

        return locations;
    }

}
