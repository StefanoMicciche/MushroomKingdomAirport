package org.example.Flight;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.Flight.Exceptions.FlightNotFoundException;
import org.example.Flight.Exceptions.InvalidFlightException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<List<FlightResponseDTO>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDTO> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FlightResponseDTO> updateFlight(
            @PathVariable Long id,
            @Valid @RequestBody FlightRequestDTO requestDTO) {
        return ResponseEntity.ok(flightService.updateFlight(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlightResponseDTO>> searchFlight(
            @RequestParam Long originAirportId,
            @RequestParam Long destinationAirportId,
            @RequestParam String departureDate,
            @RequestParam(required = false, defaultValue = "1") Integer numberOfSeats) {
        return ResponseEntity.ok(flightService.findAvailableFlights(
                originAirportId,
                destinationAirportId,
                LocalDateTime.parse(departureDate),
                numberOfSeats));
    }

    // Un solo endpoint para manejar cambios de estado
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FlightResponseDTO> updateFlightStatus(
            @PathVariable Long id,
            @RequestBody FlightStatusUpdateRequest request) {
        if (request.getNewDepartureTime() != null) {
            return ResponseEntity.ok(flightService.delayFlight(id, request.getNewDepartureTime()));
        }
        return ResponseEntity.ok(flightService.updateFlightStatus(id, request.getStatus()));
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FlightResponseDTO> cancelFlight(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.cancelFlight(id));
    }

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<String> handleFlightNotFoundException(FlightNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidFlightException.class)
    public ResponseEntity<String> handleInvalidFlightException(InvalidFlightException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
