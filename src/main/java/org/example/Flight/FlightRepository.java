package org.example.Flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
    List<FlightEntity>findByStatus(FlightStatus flightStatus);
    List<FlightEntity> findByActiveTrue();

    @Query("SELECT f FROM FlightEntity f WHERE f.status = :status AND f.active = true")
    List<FlightEntity> findActiveFlightsByStatus(@Param("status") FlightStatus status);

    @Query("SELECT f FROM FlightEntity f WHERE f.active = true " +
            "AND f.originAirport.id = :originId " +
            "AND f.destinationAirport.id = :destinationId " +
            "AND f.departureTime >= :departureTime " +
            "AND f.availableSeats >= :seats")
    List<FlightEntity> findAvailableFlights(
            Long originId,
            Long destinationId,
            LocalDateTime departureTime,
            Integer seats);
}
