package pl.lodz.p.cinema_management.filmshow.application;

import pl.lodz.p.cinema_management.filmshow.application.seat.SeatDto;
import pl.lodz.p.cinema_management.filmshow.application.film.FilmDto;
import pl.lodz.p.cinema_management.filmshow.application.cinemahall.CinemaHallDto;

import java.time.LocalDateTime;
import java.util.List;

public record FilmShowDto (
        Integer id,
        FilmDto film,
        CinemaHallDto cinemaHall,
        LocalDateTime startTime,
        List<SeatDto> seats
) {
}
