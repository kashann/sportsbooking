package com.licenta.sportsbooking.repositories;

import com.licenta.sportsbooking.model.Sport;
import com.licenta.sportsbooking.model.SportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SportRepository extends JpaRepository<Sport, Long> {

    Optional<Sport> findByName(SportType name);

    List<Sport> findByLocationId(Long id);
}
