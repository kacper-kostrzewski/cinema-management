package pl.lodz.p.cinema_management.filmshow.query.facade;

import java.util.List;

public record PageFilmShowDto(
        List<FilmShowDto> filmShows,
        Integer currentPage,
        Integer totalPages,
        Long totalElements
) {
}
