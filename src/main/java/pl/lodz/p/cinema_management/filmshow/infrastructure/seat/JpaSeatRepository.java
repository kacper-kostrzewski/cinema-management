package pl.lodz.p.cinema_management.filmshow.infrastructure.seat;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.filmshow.domain.seat.Seat;

public interface JpaSeatRepository extends JpaRepository<Seat, Integer> {
}
