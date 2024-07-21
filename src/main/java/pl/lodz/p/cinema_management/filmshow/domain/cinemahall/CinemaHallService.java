package pl.lodz.p.cinema_management.filmshow.domain.cinemahall;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaHallService {
    private final CinemaHallRepository cinemaHallRepository;

    public CinemaHallService(CinemaHallRepository cinemaHallRepository) {
        this.cinemaHallRepository = cinemaHallRepository;
    }

    public CinemaHall addCinemaHall(CinemaHall cinemaHall) {
        return cinemaHallRepository.save(cinemaHall);
    }

    public Optional<CinemaHall> getCinemaHallById(Integer id) {
        return cinemaHallRepository.findById(id);
    }

    public List<CinemaHall> getAllCinemaHalls() {
        return cinemaHallRepository.findAll();
    }

    public CinemaHall updateCinemaHall(CinemaHall cinemaHall) {
        return cinemaHallRepository.update(cinemaHall);
    }

    public void deleteCinemaHall(Integer id) {
        cinemaHallRepository.delete(id);
    }
}
