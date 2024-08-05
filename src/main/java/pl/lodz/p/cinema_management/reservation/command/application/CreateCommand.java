package pl.lodz.p.cinema_management.reservation.command.application;

import java.time.LocalDateTime;

public record CreateCommand(
        String reservationNumber,
        Integer cinemaHallId,
        Integer filmId,
        LocalDateTime reservationDateTime
) {}
