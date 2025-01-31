package org.example.Flight;

import lombok.Builder;
import lombok.Data;
import org.example.Flight.FlightStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class FlightStatusUpdateRequest {
    private FlightStatus status;
    private LocalDateTime newDepartureTime;
}

