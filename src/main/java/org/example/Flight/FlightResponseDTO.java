package org.example.Flight;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data

public class FlightResponseDTO {
    private Long id;
    private String flightNumber;
    private String originAirportCode;
    private String destinationAirportCode;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer totalSeats;
    private Integer availableSeats;
    private BigDecimal price;
    private FlightStatus status;
    private Boolean active;
}
