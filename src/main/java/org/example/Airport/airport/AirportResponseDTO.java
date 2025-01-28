package org.example.Airport.airport;

import org.example.Airport.airport.Enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponseDTO {
    private Long id;
    private String code;
    private String name;
    private String city;
    private String country;
    private AirportStatus status;
    private AirportSize size;
    private AirportType type;
    private RegionType region;
    private Boolean active;
}
