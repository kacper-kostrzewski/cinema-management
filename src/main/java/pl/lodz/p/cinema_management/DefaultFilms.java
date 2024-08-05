package pl.lodz.p.cinema_management;

import org.springframework.core.annotation.Order;
import pl.lodz.p.cinema_management.film.domain.Film;
import pl.lodz.p.cinema_management.film.domain.FilmService;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@Log
@Order(3)
public class DefaultFilms implements CommandLineRunner {

    private final FilmService filmService;

    public DefaultFilms(FilmService filmService) {
        this.filmService = filmService;
    }

    private final Film film1 = new Film(
            "The Dark Knight",
            152
    );

    private final Film film2 = new Film(
            "Inception",
            148
    );

    private final Film film3 = new Film(
            "Interstellar",
            169
    );

    @Override
    public void run(String... args) {
        try {
            addFilm(film1);
            addFilm(film2);
            addFilm(film3);
        } catch (Exception ex) {
            log.warning(ex.getMessage());
        }
    }

    private void addFilm(Film film) {
        filmService.save(film);
    }
}
