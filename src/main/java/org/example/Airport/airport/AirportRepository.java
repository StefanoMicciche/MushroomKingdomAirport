package org.example.Airport.airport;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<AirportEntity, Long>{
    Optional<AirportEntity> findByCode(String code);
    boolean existsByCode(String code);
}
