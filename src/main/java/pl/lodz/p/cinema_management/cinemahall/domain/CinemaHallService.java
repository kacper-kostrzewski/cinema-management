package pl.lodz.p.cinema_management.cinemahall.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.lodz.p.cinema_management.cinemahall.application.AvailabilityService;
import pl.lodz.p.cinema_management.cinemahall.infrastructure.storage.cinemahall.CinemaHallRepository;



import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CinemaHallService {

    private final CinemaHallRepository cinemaHallRepository;
    private final AvailabilityService availabilityService;

    public CinemaHall save(CinemaHall cinemaHall) {
        availabilityService.createCinemaHallAvailability(cinemaHall.getName());
        return cinemaHallRepository.save(cinemaHall);
    }

    public void update(CinemaHall cinemaHall) {
        cinemaHallRepository.update(cinemaHall);
    }

    public void removeById(Integer id) {
        cinemaHallRepository.remove(id);
    }

    public CinemaHall findByName(String name) {
        return cinemaHallRepository.findByName(name)
                .orElseThrow(CinemaHallNotFoundException::new);
    }

    public CinemaHall findById(Integer id) {
        return cinemaHallRepository.findById(id)
                .orElseThrow(CinemaHallNotFoundException::new);
    }

    public List<CinemaHall> getAllCinemaHalls() {
        return cinemaHallRepository.findAll();
    }

}
