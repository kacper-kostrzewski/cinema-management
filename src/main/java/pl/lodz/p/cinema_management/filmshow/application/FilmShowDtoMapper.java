package pl.lodz.p.cinema_management.filmshow.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import pl.lodz.p.cinema_management.filmshow.application.cinemahall.CinemaHallDtoMapper;
import pl.lodz.p.cinema_management.filmshow.application.seat.SeatDtoMapper;
import pl.lodz.p.cinema_management.filmshow.application.film.FilmDtoMapper;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShow;
import pl.lodz.p.cinema_management.filmshow.domain.cinemahall.CinemaHall;
import pl.lodz.p.cinema_management.filmshow.domain.film.Film;

@Mapper(componentModel = "spring", uses = {FilmDtoMapper.class, CinemaHallDtoMapper.class, SeatDtoMapper.class})
public interface FilmShowDtoMapper {

    @Mapping(source = "film", target = "filmId", qualifiedByName = "filmToId")
    @Mapping(source = "cinemaHall", target = "cinemaHallId", qualifiedByName = "cinemaHallToId")
    AddFilmShowDto addFilmShowDto(FilmShow filmShow);

    @Mapping(source = "filmId", target = "film", qualifiedByName = "filmFromId")
    @Mapping(source = "cinemaHallId", target = "cinemaHall", qualifiedByName = "cinemaHallFromId")
    FilmShow toDomain(AddFilmShowDto addFilmShowDto);

    @Named("filmFromId")
    default Film mapFilmFromId(Integer id) {
        if (id == null) {
            return null;
        }
        Film film = new Film();
        film.setId(id);
        return film;
    }

    @Named("cinemaHallFromId")
    default CinemaHall mapCinemaHallFromId(Integer id) {
        if (id == null) {
            return null;
        }
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setId(id);
        return cinemaHall;
    }

    @Named("filmToId")
    default Integer mapFilmToId(Film film) {
        if (film == null) {
            return null;
        }
        return film.getId();
    }

    @Named("cinemaHallToId")
    default Integer mapCinemaHallToId(CinemaHall cinemaHall) {
        if (cinemaHall == null) {
            return null;
        }
        return cinemaHall.getId();
    }

    FilmShowDto toDto(FilmShow filmShow);
}
