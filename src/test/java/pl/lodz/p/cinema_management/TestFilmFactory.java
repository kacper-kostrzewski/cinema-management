package pl.lodz.p.cinema_management;

import pl.lodz.p.cinema_management.film.domain.Film;

public class TestFilmFactory {
    public static Film createFilm() {
        return new Film("Film name", 60);
    }
}
