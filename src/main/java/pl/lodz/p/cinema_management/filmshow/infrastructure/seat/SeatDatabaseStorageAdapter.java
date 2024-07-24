package pl.lodz.p.cinema_management.filmshow.infrastructure.seat;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.filmshow.domain.seat.Seat;
import pl.lodz.p.cinema_management.filmshow.domain.seat.SeatRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class SeatDatabaseStorageAdapter implements SeatRepository {

    private final JpaSeatRepository jpaSeatRepository;

    public SeatDatabaseStorageAdapter(JpaSeatRepository jpaSeatRepository) {
        this.jpaSeatRepository = jpaSeatRepository;
    }

    @Override
    public void save(Seat seat) {
        jpaSeatRepository.save(seat);
    }

    @Override
    public Optional<Seat> findById(Integer id) {
        return jpaSeatRepository.findById(id);
    }

    @Override
    public List<Seat> findAll() {
        return jpaSeatRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
