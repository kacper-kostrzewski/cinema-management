package pl.lodz.p.cinema_management.reservation.command.application;

import pl.lodz.p.cinema_management.reservation.command.domain.CinemaHall;

public interface CinemaHallService {
    CinemaHall getCinemaHallById(Integer id);
}
