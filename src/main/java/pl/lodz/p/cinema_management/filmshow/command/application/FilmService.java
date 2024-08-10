package pl.lodz.p.cinema_management.filmshow.command.application;

import pl.lodz.p.cinema_management.filmshow.command.domain.Film;

public interface FilmService {
    Film getFilmById(Integer id);
    Film getFilmByName(String name);
}
