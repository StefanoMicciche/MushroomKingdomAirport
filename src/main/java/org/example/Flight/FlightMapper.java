package org.example.Flight;

import org.springframework.stereotype.Component;

@Component
public class FlightMapper {

    public FlightResponseDTO flightResponseDTO(FlightEntity flightEntity){
        FlightResponseDTO flightResponseDTO = new FlightResponseDTO();
        flightResponseDTO.setId(flightEntity.getId());
        flightResponseDTO.setFlightNumber(flightEntity.getFlightNumber());
        flightResponseDTO.setOriginAirportCode(flightResponseDTO.getOriginAirportCode());
        flightResponseDTO.setDestinationAirportCode(flightResponseDTO.getDestinationAirportCode());
        flightResponseDTO.setDepartureTime(flightEntity.getDepartureTime());
        flightResponseDTO.setArrivalTime(flightEntity.getArrivalTime());
        flightResponseDTO.setTotalSeats(flightEntity.getTotalSeats());
        flightResponseDTO.setAvailableSeats(flightEntity.getAvailableSeats());
        flightResponseDTO.setPrice(flightEntity.getPrice());
        flightResponseDTO.setActive(flightEntity.getActive());
        return flightResponseDTO;
    }

    public FlightEntity flightEntity(FlightRequestDTO flightRequestDTO){
        FlightEntity flightEntity = new FlightEntity();
        flightEntity.setFlightNumber(flightRequestDTO.getFlightNumber());
        flightEntity.setDepartureTime(flightRequestDTO.getDepartureTime());
        flightEntity.setArrivalTime(flightRequestDTO.getArrivalTime());
        flightEntity.setTotalSeats(flightRequestDTO.getTotalSeats());
        flightEntity.setAvailableSeats(flightRequestDTO.getTotalSeats());
        flightEntity.setPrice(flightRequestDTO.getPrice());
        flightEntity.setActive(true);
        return flightEntity;
    }
}
