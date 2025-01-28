package org.example.Airport.airport;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @GetMapping
    public ResponseEntity<List<AirportResponseDTO>> getAllAirports(){
        return ResponseEntity.ok(airportService.getAllAirports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponseDTO> getAirportById(@PathVariable Long id){
        return ResponseEntity.ok(airportService.getAirportById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole(´ADMIN´)")
    public ResponseEntity<AirportResponseDTO> createAirport(@Valid @RequestBody AirpotRequestDTO airpotRequestDTO){
        return new ResponseEntity<>(airportService.createAirport(airpotRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole(´ADMIN´)")
    public ResponseEntity<AirportResponseDTO> updateAirport(@PathVariable Long id, @Valid @RequestBody AirpotRequestDTO airpotRequestDTO){
        return ResponseEntity.ok(airportService.updateAirport(id,airpotRequestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(´ADMIN´)")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        airportService.deleteAirport(id);
        return ResponseEntity.noContent().build();
    }
}
