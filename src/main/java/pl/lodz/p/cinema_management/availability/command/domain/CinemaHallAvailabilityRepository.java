package pl.lodz.p.cinema_management.availability.command.domain;

import java.util.Optional;

public interface CinemaHallAvailabilityRepository {

    CinemaHallAvailability save(CinemaHallAvailability cinemaHallAvailability);
    void remove(String cinemaHallName);
    Optional<CinemaHallAvailability> findByCinemaHallName(String cinemaHallName);

}
