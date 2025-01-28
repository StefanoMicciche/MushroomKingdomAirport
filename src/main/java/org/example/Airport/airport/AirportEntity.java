package org.example.Airport.airport;

import jakarta.persistence.*;
import lombok.*;
import org.example.Airport.airport.Enums.AirportSize;
import org.example.Airport.airport.Enums.AirportStatus;
import org.example.Airport.airport.Enums.AirportType;
import org.example.Airport.airport.Enums.RegionType;

@Entity
@Table(name = "airports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AirportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirportStatus airportStatus = AirportStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirportType airportType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirportSize airportSize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegionType regionType;
}
