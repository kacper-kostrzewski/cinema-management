package pl.lodz.p.cinema_management.filmshow.application.film;

import java.time.LocalDate;

public record FilmDto(
        Integer id,
        String title,
        String genre,
        String director,
        String stars,
        Integer duration,
        LocalDate releaseDate,
        String production,
        String synopsis
) {
}