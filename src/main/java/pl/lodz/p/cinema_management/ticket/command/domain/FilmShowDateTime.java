package pl.lodz.p.cinema_management.ticket.command.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record FilmShowDateTime(LocalDateTime filmShowDateTime) {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static FilmShowDateTime of(LocalDateTime filmShowDateTime) {
        return new FilmShowDateTime(filmShowDateTime);
    }

    public String asString() {
        return filmShowDateTime.format(FORMATTER);
    }

    public LocalDateTime asLocalDateTime() {
        return filmShowDateTime;
    }

}