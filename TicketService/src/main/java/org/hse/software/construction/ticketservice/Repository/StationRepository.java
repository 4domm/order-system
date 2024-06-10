package org.hse.software.construction.ticketservice.Repository;

import org.hse.software.construction.ticketservice.Model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface StationRepository extends JpaRepository<Station, UUID> {
    Station findByName(String name);
}
