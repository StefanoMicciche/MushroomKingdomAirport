package org.example.Flight;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Airport.airport.AirportRepository;
import org.example.Flight.Exceptions.FlightNotFoundException;
import org.example.Flight.Exceptions.InvalidFlightException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final FlightMapper flightMapper;

    private static final int MIN_FLIGHT_DURATION_MINUTES = 30;
    private static final int MAX_ADVANCE_BOOKING_DAYS = 365;
    private static final int MIN_DEPARTURE_HOURS = 2;
    private static final int MAX_FLIGHT_DURATION_HOURS = 20;
    private static final int EARLIEST_DEPARTURE_HOUR = 4;
    private static final int LATEST_DEPARTURE_HOUR = 23;

    @Transactional(readOnly = true)
    public List<FlightResponseDTO> getAllFlights(){
        return flightRepository.findByActiveTrue().stream()
                .map(flightMapper::flightResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FlightResponseDTO getFlightById(Long id){
        return flightRepository.findById(id)
                .map(flightMapper::flightResponseDTO)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with id: " + id));
    }

    @Transactional
    public FlightResponseDTO createFlight(FlightRequestDTO flightRequestDTO){
        validateFlightTimes(flightRequestDTO);

        var originAirport = airportRepository.findById(flightRequestDTO.getOriginAirportId())
                .orElseThrow(() -> new FlightNotFoundException("Origin airport not found"));

        var destinationAirport = airportRepository.findById(flightRequestDTO.getDestinationAirportId())
                .orElseThrow(() -> new FlightNotFoundException("Destination airport not found"));

        if (originAirport.equals(destinationAirport)){
            throw new InvalidFlightException("Origin and destination airports cannot be the same");
        }

        var flightEntity = flightMapper.flightEntity(flightRequestDTO);
        flightEntity.setOriginAirport(originAirport);
        flightEntity.setDestinationAirport(destinationAirport);

        return flightMapper.flightResponseDTO(flightRepository.save(flightEntity));
    }

    @Transactional
    public FlightResponseDTO updateFlight(Long id, FlightRequestDTO flightRequestDTO){
        validateFlightTimes(flightRequestDTO);

        var flight = flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with id: " + id));

        var originAirport = airportRepository.findById(flightRequestDTO.getOriginAirportId())
                .orElseThrow(() -> new FlightNotFoundException("Origin airport not found"));

        var destinationAirport = airportRepository.findById(flightRequestDTO.getDestinationAirportId())
                .orElseThrow(() -> new InvalidFlightException("Destination airport not found"));

        if (originAirport.equals(destinationAirport)){
            throw new InvalidFlightException("Origin and destination airport cannot be the same");
        }

        flight.setFlightNumber(flightRequestDTO.getFlightNumber());
        flight.setOriginAirport(originAirport);
        flight.setDestinationAirport(destinationAirport);
        flight.setDepartureTime(flightRequestDTO.getDepartureTime());
        flight.setArrivalTime(flightRequestDTO.getArrivalTime());
        flight.setTotalSeats(flightRequestDTO.getTotalSeats());
        flight.setPrice(flightRequestDTO.getPrice());

        return flightMapper.flightResponseDTO(flightRepository.save(flight));
    }

    @Transactional
    public void deleteFlight(Long id){
        var flight = flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with id: " + id));

        flight.setActive(false);
        flightRepository.save(flight);
    }

    @Transactional(readOnly = true)
    public List<FlightResponseDTO> findAvailableFlights(
            Long originAirportid,
            Long destinationAirportId,
            LocalDateTime departureDate,
            Integer numberOfSeats){
        if (originAirportid.equals(destinationAirportId)){
            throw new InvalidFlightException("Origin and destination airport cannot be the same");
        }

        if (departureDate.isBefore(LocalDateTime.now())){
            throw new InvalidFlightException("Departure date cannot be in the past");
        }

        if (numberOfSeats <= 0){
            throw new InvalidFlightException("number of seats must be greater than 0");
        }
        return flightRepository.findAvailableFlights(
                originAirportid,
                destinationAirportId,
                departureDate,
                numberOfSeats)
                .stream()
                .map(flightMapper::flightResponseDTO)
                .collect(Collectors.toList());
    }

    private void validateFlightTimes(FlightRequestDTO flightRequestDTO){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departureTime = flightRequestDTO.getDepartureTime();
        LocalDateTime arrivalTime = flightRequestDTO.getArrivalTime();

        List<String> validationErrors = new ArrayList<>();

        if (departureTime == null || arrivalTime == null){
            throw new InvalidFlightException("Departure and arrival time cannot be null");
        }

        if (departureTime.isBefore(now)){
            validationErrors.add("Departure time cannot be in the past");
        }

        if (departureTime.isBefore(now.plusHours(MIN_DEPARTURE_HOURS))){
            validationErrors.add("Departure time must be at least: " + MIN_DEPARTURE_HOURS + "hours in the future");
        }

        if (arrivalTime.isBefore(departureTime) || arrivalTime.isEqual(departureTime)){
            validationErrors.add("Arrival time must be after departure time");
        }

        Duration flightDuration = Duration.between(departureTime, arrivalTime);
        if (flightDuration.toMinutes() < MAX_FLIGHT_DURATION_HOURS){
            validationErrors.add("Flight duration must be at least: " + MAX_FLIGHT_DURATION_HOURS + " minutes");
        }

        if (flightDuration.toHours() > MAX_FLIGHT_DURATION_HOURS){
            validationErrors.add("Flight duration cannot exceed " + MAX_FLIGHT_DURATION_HOURS + " hours");
        }

        int departureHour = departureTime.getHour();
        if (departureHour < EARLIEST_DEPARTURE_HOUR || departureHour > LATEST_DEPARTURE_HOUR){
            validationErrors.add("Flights can only depart between: " + EARLIEST_DEPARTURE_HOUR + "00 and " + LATEST_DEPARTURE_HOUR + "00");
        }

        if (departureTime.isAfter(now.plusDays(MAX_ADVANCE_BOOKING_DAYS))){
            validationErrors.add("Flights cannot be scheduled more than: " + MAX_ADVANCE_BOOKING_DAYS + " days in advance");
        }

        if (!validationErrors.isEmpty()){
            throw new InvalidFlightException("Flight validation failed: " + String.join(",", validationErrors));
        }
    }
}
