package pl.lodz.p.cinema_management.cinemahall.infrastructure.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;

public interface JpaCinemaHallRepository extends JpaRepository<CinemaHall, Integer> {
}
