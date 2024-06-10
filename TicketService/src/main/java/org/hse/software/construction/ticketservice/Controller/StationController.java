package org.hse.software.construction.ticketservice.Controller;

import org.hse.software.construction.ticketservice.Model.Station;
import org.hse.software.construction.ticketservice.Service.Impl.StationServiceImpl;
import org.hse.software.construction.ticketservice.Utility.AddStationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class StationController {
    private StationServiceImpl stationService;

    @PostMapping("/add-station")
    public ResponseEntity<String> addStation(@RequestBody AddStationRequest request) {
        try {
            stationService.createStation(request.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Station added");
    }

    @GetMapping("/get-stations")
    public ResponseEntity<List<Station>> getStations() {
        try {
            return ResponseEntity.ok(stationService.getAll());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
