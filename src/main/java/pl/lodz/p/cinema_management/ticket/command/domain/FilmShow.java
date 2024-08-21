package pl.lodz.p.cinema_management.ticket.command.domain;

import java.time.LocalDateTime;

public record FilmShow(
        String filmName,
        String cinemaHallName,
        LocalDateTime filmShowDateTime
) {}
