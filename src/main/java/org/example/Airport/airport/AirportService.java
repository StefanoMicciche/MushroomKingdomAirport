package org.example.Airport.airport;

import lombok.RequiredArgsConstructor;
import org.example.Airport.airport.Exceptions.AirportNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AirportService {

    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    @Transactional(readOnly = true)
    public List<AirportResponseDTO> getAllAirports(){
        return airportRepository.findAll()
                .stream()
                .map(airportMapper::airportResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AirportResponseDTO getAirportById(Long id){
        return airportRepository.findById(id)
                .map(airportMapper::airportResponseDTO)
                .orElseThrow(()-> new AirportNotFoundException("Airport not found with id: " + id));
    }

    @Transactional
    public AirportResponseDTO createAirport(AirpotRequestDTO airpotRequestDTO){
        if (airportRepository.existsByCode(airpotRequestDTO.getCode())){
            throw new IllegalStateException("Airport with code: " + airpotRequestDTO.getCode() + " already exists");
        }
        AirportEntity airportEntity = airportMapper.toEntity(airpotRequestDTO);
        AirportEntity savedAirport = airportRepository.save(airportEntity);
        return airportMapper.airportResponseDTO(savedAirport);
    }

    @Transactional
    public AirportResponseDTO updateAirport(Long id, AirpotRequestDTO airpotRequestDTO){
        AirportEntity airportEntity = airportRepository.findById(id)
                .orElseThrow(() -> new AirportNotFoundException("Airport not found with id: " + id));

        airportEntity.setCode(airpotRequestDTO.getCode());
        airportEntity.setName(airpotRequestDTO.getName());
        airportEntity.setCity(airpotRequestDTO.getCity());
        airportEntity.setCountry(airpotRequestDTO.getCountry());

        AirportEntity updatedAirport = airportRepository.save(airportEntity);
        return airportMapper.airportResponseDTO(updatedAirport);
    }

    @Transactional
    public void deleteAirport(Long id){
        AirportEntity airportEntity = airportRepository.findById(id)
            .orElseThrow(()-> new AirportNotFoundException("Airport not found with: " + id));
        airportEntity.setActive(false);
        airportRepository.save(airportEntity);
        }
    }

