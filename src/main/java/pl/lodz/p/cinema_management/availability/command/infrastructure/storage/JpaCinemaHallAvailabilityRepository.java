package pl.lodz.p.cinema_management.availability.command.infrastructure.storage;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import pl.lodz.p.cinema_management.availability.command.domain.CinemaHallAvailability;

import java.util.Optional;

public interface JpaCinemaHallAvailabilityRepository extends JpaRepository<CinemaHallAvailability, Integer> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<CinemaHallAvailability> findByCinemaHallName(String cinemaHallName);
}
