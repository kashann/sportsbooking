package com.licenta.sportsbooking.repositories;

import com.licenta.sportsbooking.model.Sport;
import com.licenta.sportsbooking.model.SportType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class SportRepositoryIT {

    @Autowired
    SportRepository repository;

    @Test
    void findByName() {
        assertNotNull(repository);
        Optional<Sport> sportOptional = repository.findByName(SportType.KITEBOARDING);
        assertEquals(SportType.KITEBOARDING, sportOptional.get().getName());
    }

    @Test
    void findByLocationId() {
        assertNotNull(repository);
        List<Sport> sportOptional = repository.findByLocationId(1L);
        assertEquals(1L, sportOptional.get(0).getLocation().getId());
    }
}