package pl.lodz.p.cinema_management.reservation.command.application;

import pl.lodz.p.cinema_management.reservation.command.domain.Film;

public interface FilmService {
    Film getFilmById(Integer id);
}
