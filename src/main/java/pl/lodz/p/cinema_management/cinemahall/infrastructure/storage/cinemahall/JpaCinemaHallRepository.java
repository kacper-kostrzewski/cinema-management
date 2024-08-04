package pl.lodz.p.cinema_management.cinemahall.infrastructure.storage.cinemahall;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;

import java.util.Optional;

public interface JpaCinemaHallRepository extends JpaRepository<CinemaHall, Integer> {
    Optional<CinemaHall> findByName(String name);
}
