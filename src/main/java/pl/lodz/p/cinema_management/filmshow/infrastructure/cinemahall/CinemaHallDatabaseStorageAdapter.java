package pl.lodz.p.cinema_management.filmshow.infrastructure.cinemahall;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.filmshow.domain.cinemahall.CinemaHall;
import pl.lodz.p.cinema_management.filmshow.domain.cinemahall.CinemaHallNotFoundException;
import pl.lodz.p.cinema_management.filmshow.domain.cinemahall.CinemaHallRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class CinemaHallDatabaseStorageAdapter implements CinemaHallRepository {

    private final JpaCinemaHallRepository jpaCinemaHallRepository;

    public CinemaHallDatabaseStorageAdapter(JpaCinemaHallRepository jpaCinemaHallRepository) {
        this.jpaCinemaHallRepository = jpaCinemaHallRepository;
    }

    @Override
    public CinemaHall save(CinemaHall cinemaHall) {
        return jpaCinemaHallRepository.save(cinemaHall);
    }

    @Override
    public Optional<CinemaHall> findById(Integer id) {
        return jpaCinemaHallRepository.findById(id);
    }

    @Override
    public List<CinemaHall> findAll() {
        return jpaCinemaHallRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public CinemaHall update(CinemaHall cinemaHall) {
        Optional<CinemaHall> cinemaHallToUpdate = jpaCinemaHallRepository.findById(cinemaHall.getId());
        if (cinemaHallToUpdate.isPresent()) {
            return jpaCinemaHallRepository.save(cinemaHall);
        }
        throw new CinemaHallNotFoundException();
    }

    @Override
    public void delete(Integer id) {
        jpaCinemaHallRepository.deleteById(id);
    }

}
