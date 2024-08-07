package pl.lodz.p.cinema_management.filmshow.command.application;

import java.time.LocalDateTime;

public record CreateCommand(
        String filmShowNumber,
        Integer cinemaHallId,
        Integer filmId,
        LocalDateTime filmShowDateTime
) {}
