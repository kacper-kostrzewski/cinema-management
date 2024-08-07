package pl.lodz.p.cinema_management.filmshow.command.domain;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class PageFilmShow implements Serializable {
    List<FilmShow> filmShows;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;
}