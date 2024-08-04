package pl.lodz.p.cinema_management.availability.command.infrastructure.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.availability.command.domain.CinemaHallAlreadyExistsException;
import pl.lodz.p.cinema_management.availability.command.domain.CinemaHallAvailability;
import pl.lodz.p.cinema_management.availability.command.domain.CinemaHallAvailabilityRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Log
@Component
public class CinemaHallAvailabilityStorageAdapter implements CinemaHallAvailabilityRepository {

    private final JpaCinemaHallAvailabilityRepository cinemaHallAvailabilityRepository;

    @Override
    public CinemaHallAvailability save(CinemaHallAvailability cinemaHallAvailability) {
       try {
           CinemaHallAvailability saved = cinemaHallAvailabilityRepository.save(cinemaHallAvailability);
           log.info("Saved cinemaHallAvailability: " + saved);
           return saved;
       } catch (DataIntegrityViolationException ex) {
           log.warning("Cinema Hall Availability with name " + cinemaHallAvailability.getCinemaHallName() + " already exits in db");
           throw new CinemaHallAlreadyExistsException();
       }
    }

    @Override
    public Optional<CinemaHallAvailability> findByCinemaHallName(String cinemaHallName) {
        return cinemaHallAvailabilityRepository.findByCinemaHallName(cinemaHallName);
    }

}
