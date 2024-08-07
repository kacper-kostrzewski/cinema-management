package pl.lodz.p.cinema_management.filmshow.command.application;

import pl.lodz.p.cinema_management.filmshow.command.domain.CinemaHall;

public interface CinemaHallService {
    CinemaHall getCinemaHallById(Integer id);
}
