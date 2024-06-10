package org.hse.software.construction.ticketservice.Service.Impl;

import org.hse.software.construction.ticketservice.Model.Station;
import org.hse.software.construction.ticketservice.Repository.StationRepository;
import org.hse.software.construction.ticketservice.Service.StationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class StationServiceImpl implements StationService {
    private StationRepository stationRepository;
    @Override
    @Transactional
    public void createStation(String name) {
        if (stationRepository.findByName(name) != null) {
            throw new IllegalArgumentException("Station already exists");
        }
        Station station = Station.builder().name(name).build();
        log.info("created station");
        stationRepository.save(station);
    }
    @Override
    public Station getStationById(UUID id) {
        return stationRepository.findById(id).orElse(null);
    }
    @Override
    public List<Station> getAll() {
        return stationRepository.findAll();
    }
}
