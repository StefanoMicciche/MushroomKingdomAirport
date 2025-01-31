package org.example.Airport.airport;

import org.springframework.stereotype.Component;

@Component
public class AirportMapper {

    public AirportEntity toEntity(AirpotRequestDTO requestDTO){
        return AirportEntity.builder()
                .code(requestDTO.getCode())
                .name(requestDTO.getName())
                .city(requestDTO.getCity())
                .country(requestDTO.getCountry())
                .active(true)
                .build();
    }

    public AirportResponseDTO airportResponseDTO(AirportEntity airportEntity){
        return AirportResponseDTO.builder()
                .id(airportEntity.getId())
                .code(airportEntity.getCode())
                .name(airportEntity.getName())
                .city(airportEntity.getCity())
                .country(airportEntity.getCountry())
                .active(airportEntity.getActive())
                .build();
    }

    public byte[] writeValueAsString(AirpotRequestDTO airpotRequestDTO) {
        return null;
    }
}
