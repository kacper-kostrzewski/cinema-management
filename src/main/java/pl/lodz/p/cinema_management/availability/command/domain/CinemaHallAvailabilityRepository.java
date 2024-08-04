package pl.lodz.p.cinema_management.availability.command.domain;

import java.util.Optional;

public interface CinemaHallAvailabilityRepository {

    CinemaHallAvailability save(CinemaHallAvailability cinemaHallAvailability);
    Optional<CinemaHallAvailability> findByCinemaHallName(String cinemaHallName);

}
