package com.licenta.sportsbooking.services;

import com.licenta.sportsbooking.converters.TownToTownDtoConverter;
import com.licenta.sportsbooking.dto.TownDTO;
import com.licenta.sportsbooking.repositories.TownRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final TownToTownDtoConverter townToTownDtoConverter;

    @Override
    public List<TownDTO> getTowns() {
        log.debug("I'm getting all towns");

        return townRepository.findAll()
                .stream()
                .map(townToTownDtoConverter::convert)
                .collect(Collectors.toList());
    }
}
