package org.hse.software.construction.ticketservice.Service;

import org.hse.software.construction.ticketservice.Model.Station;

import java.util.List;
import java.util.UUID;

public interface StationService {
    void createStation(String name);
    Station getStationById(UUID id);
    List<Station> getAll();
}
