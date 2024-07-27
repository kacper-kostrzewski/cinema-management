package pl.lodz.p.cinema_management.filmshow.application;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.filmshow.application.cinemahall.CinemaHallDto;
import pl.lodz.p.cinema_management.filmshow.application.cinemahall.CinemaHallDtoMapper;
import pl.lodz.p.cinema_management.filmshow.application.film.FilmDto;
import pl.lodz.p.cinema_management.filmshow.application.film.FilmDtoMapper;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowService;
import pl.lodz.p.cinema_management.filmshow.domain.cinemahall.CinemaHallService;
import pl.lodz.p.cinema_management.filmshow.domain.film.FilmService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class DefaultFilmShows implements CommandLineRunner {
    private final FilmDtoMapper filmDtoMapper;
    private final CinemaHallDtoMapper cinemaHallDtoMapper;
    private final FilmShowDtoMapper filmShowDtoMapper;
    private final FilmShowService filmShowService;
    private final CinemaHallService cinemaHallService;
    private final FilmService filmService;

    private final CinemaHallDto cinemaHalls[] = {
            new CinemaHallDto(null, "Hall A", 30),
            new CinemaHallDto(null, "Hall B", 20),
            new CinemaHallDto(null, "Hall C", 40)
    };

    private final FilmDto films[] = {
            new FilmDto(null, "Inception", "Sci-Fi", "Christopher Nolan", "Leonardo DiCaprio, Joseph Gordon-Levitt", 148, LocalDate.of(2010, 7, 16), "Warner Bros.", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO."),
            new FilmDto(null, "The Matrix", "Sci-Fi", "Lana Wachowski, Lilly Wachowski", "Keanu Reeves, Laurence Fishburne", 136, LocalDate.of(1999, 3, 31), "Warner Bros.", "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."),
            new FilmDto(null, "Interstellar", "Sci-Fi", "Christopher Nolan", "Matthew McConaughey, Anne Hathaway", 169, LocalDate.of(2014, 11, 7), "Paramount Pictures", "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.")
    };

    private final CreateFilmShowDto filmShows[] = {
            new CreateFilmShowDto(null, 1, 1, LocalDateTime.of(2024, 8, 1, 18, 0)),
            new CreateFilmShowDto(null, 2, 2, LocalDateTime.of(2024, 8, 1, 20, 0)),
            new CreateFilmShowDto(null, 3, 3, LocalDateTime.of(2024, 8, 2, 21, 0)),
            new CreateFilmShowDto(null, 1, 2, LocalDateTime.of(2024, 8, 2, 22, 0))
    };

    @Override
    public void run(String... args) throws Exception {
        for (CinemaHallDto cinemaHall : cinemaHalls) {
            cinemaHallService.addCinemaHall(cinemaHallDtoMapper.toDomain(cinemaHall));
        }
        for (FilmDto film : films) {
            filmService.addFilm(filmDtoMapper.toDomain(film));
        }
        for (CreateFilmShowDto filmShow : filmShows) {
            filmShowService.addFilmShow(filmShowDtoMapper.toDomain(filmShow));
        }
    }
}
