package pl.lodz.p.cinema_management.reservation.command.application;

import pl.lodz.p.cinema_management.reservation.command.domain.CinemaHall;

public interface HallService {
    CinemaHall getCinemaHallById(Integer id);
}
