package pl.lodz.p.cinema_management.cinemahall.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.lodz.p.cinema_management.cinemahall.application.AvailabilityService;
import pl.lodz.p.cinema_management.cinemahall.infrastructure.storage.cinemahall.CinemaHallRepository;



import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log
public class CinemaHallService {

    private final CinemaHallRepository cinemaHallRepository;
    private final AvailabilityService availabilityService;

    public CinemaHall save(CinemaHall cinemaHall) {
        log.info("Attempting to save CinemaHall: " + cinemaHall.toString());
        availabilityService.createCinemaHallAvailability(cinemaHall.getName());
        CinemaHall savedCinemaHall = cinemaHallRepository.save(cinemaHall);
        log.info("CinemaHall saved successfully: " + savedCinemaHall.toString());
        return savedCinemaHall;
    }

    public void update(CinemaHall cinemaHall) {
        log.info("Attempting to update CinemaHall with ID: " + cinemaHall.getId());
        cinemaHallRepository.update(cinemaHall);
        log.info("CinemaHall updated successfully with ID: " + cinemaHall.getId());
    }

    public void removeById(Integer id) {
        log.info("Attempting to remove CinemaHall with ID: " + id);
        CinemaHall cinemaHall = cinemaHallRepository.findById(id)
                .orElseThrow(() -> {
                    log.warning("CinemaHall not found with ID: " + id);
                    return new CinemaHallNotFoundException();
                });
        availabilityService.removeCinemaHallAvailability(cinemaHall.getName());
        cinemaHallRepository.remove(id);
        log.info("CinemaHall removed successfully with ID: " + id);
    }

    public CinemaHall findByName(String name) {
        log.info("Searching for CinemaHall with name: " + name);
        CinemaHall cinemaHall = cinemaHallRepository.findByName(name)
                .orElseThrow(() -> {
                    log.warning("CinemaHall not found with name: " + name);
                    return new CinemaHallNotFoundException();
                });
        log.info("CinemaHall found: " + cinemaHall.toString());
        return cinemaHall;
    }

    public CinemaHall findById(Integer id) {
        log.info("Searching for CinemaHall with ID: " + id);
        CinemaHall cinemaHall = cinemaHallRepository.findById(id)
                .orElseThrow(() -> {
                    log.warning("CinemaHall not found with ID: " + id);
                    return new CinemaHallNotFoundException();
                });
        log.info("CinemaHall found: " + cinemaHall.toString());
        return cinemaHall;
    }

    public List<CinemaHall> getAllCinemaHalls() {
        log.info("Retrieving all CinemaHalls");
        List<CinemaHall> cinemaHalls = cinemaHallRepository.findAll();
        log.info("Total CinemaHalls found: " + cinemaHalls.size());
        return cinemaHalls;
    }
}
