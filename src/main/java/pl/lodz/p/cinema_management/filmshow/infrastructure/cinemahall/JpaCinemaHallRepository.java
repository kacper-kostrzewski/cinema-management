package pl.lodz.p.cinema_management.filmshow.infrastructure.cinemahall;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.filmshow.domain.cinemahall.CinemaHall;

public interface JpaCinemaHallRepository extends JpaRepository<CinemaHall, Integer> {
}
