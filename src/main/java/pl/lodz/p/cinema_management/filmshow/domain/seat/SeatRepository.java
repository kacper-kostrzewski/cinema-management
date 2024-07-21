package pl.lodz.p.cinema_management.filmshow.domain.seat;

import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.annotation.architecture.SecondaryPort;

import java.util.List;
import java.util.Optional;

@Repository
@SecondaryPort
public interface SeatRepository {
    Optional<Seat> findById(Integer seatId);
    List<Seat> findAll();
    void save(Seat seat);
}
