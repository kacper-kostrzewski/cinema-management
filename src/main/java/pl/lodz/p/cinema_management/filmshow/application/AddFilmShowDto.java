package pl.lodz.p.cinema_management.filmshow.application;

import java.time.LocalDateTime;

public record AddFilmShowDto(
        Integer id,
        Integer filmId,
        Integer cinemaHallId,
        LocalDateTime startTime
) {
}
