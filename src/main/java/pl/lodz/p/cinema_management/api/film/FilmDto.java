package pl.lodz.p.cinema_management.api.film;

import java.time.LocalDate;

public record FilmDto(
        Integer id,
        String title,
        String genre,
        String director,
        String cast,
        Integer duration,
        LocalDate releaseDate,
        String production,
        String synopsis
) {
}