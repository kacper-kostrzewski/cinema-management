package pl.lodz.p.cinema_management.cinemahall.infrastructure.storage.cinemahall;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHallAlreadyExistsException;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Log
@Component
public class CinemaHallStorageAdapter implements CinemaHallRepository {

    private final JpaCinemaHallRepository cinemaHallRepository;

    @Override
    public CinemaHall save(final CinemaHall cinemaHall) {
        try {
            CinemaHall saved = cinemaHallRepository.save(cinemaHall);
            log.info("Saved entity " + saved);
            return saved;
        } catch (DataIntegrityViolationException ex) {
            log.warning("Cinema hall " + cinemaHall.getName() + " already exits in db");
            throw new CinemaHallAlreadyExistsException();
        }
    }

    @Override
    public void update(final CinemaHall cinemaHall) {
        cinemaHallRepository.findById(cinemaHall.getId()).ifPresent(cinemaHallEntity -> cinemaHallRepository.save(cinemaHall));
    }

    @Override
    public void remove(final Integer id) {
        cinemaHallRepository.findById(id).ifPresent(cinemaHallEntity -> cinemaHallRepository.deleteById(id));
    }

    @Override
    public Optional<CinemaHall> findByName(String name) {
        return cinemaHallRepository.findByName(name);
    }

    @Override
    public Optional<CinemaHall> findById(final Integer id) {
        return cinemaHallRepository.findById(id);
    }

    @Override
    public List<CinemaHall> findAll() {
        return cinemaHallRepository.findAll();
    }
}
