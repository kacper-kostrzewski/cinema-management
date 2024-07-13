package pl.lodz.p.cinema_management.domain.cinemahall;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CinemaHallService {
    private final CinemaHallRepository cinemaHallRepository;

    public CinemaHall addCinemaHall(CinemaHall cinemaHall) {
        return cinemaHallRepository.save(cinemaHall);
    }

    public Optional<CinemaHall> getCinemaHallById(Integer id) {
        return cinemaHallRepository.findById(id);
    }

    public List<CinemaHall> getAllCinemaHalls() {
        return cinemaHallRepository.findAll();
    }

    public CinemaHall updateCinemaHall(Integer id, CinemaHall cinemaHall) {
        return cinemaHallRepository.update(id, cinemaHall);
    }

    public void deleteCinemaHall(Integer id) {
        cinemaHallRepository.delete(id);
    }
}
