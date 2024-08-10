package pl.lodz.p.cinema_management.filmshow.command.infrastructure.film;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.film.domain.Film;
import pl.lodz.p.cinema_management.filmshow.command.application.FilmService;


@Component
@RequiredArgsConstructor
public class FilmFacade implements FilmService {

    private final pl.lodz.p.cinema_management.film.domain.FilmService filmService;
    private final FilmMapper filmMapper;

    @Override
    public pl.lodz.p.cinema_management.filmshow.command.domain.Film getFilmById(Integer id) {
        Film film = filmService.findById(id);
        return filmMapper.toReservationContext(film);
    }

    @Override
    public pl.lodz.p.cinema_management.filmshow.command.domain.Film getFilmByName(String name) {
        Film film = filmService.findByName(name);
        return filmMapper.toReservationContext(film);
    }
}
