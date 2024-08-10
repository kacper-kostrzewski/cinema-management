package pl.lodz.p.cinema_management.filmshow.command.domain;

import java.util.Optional;

public interface FilmShowRepository {
    FilmShow save(FilmShow filmShow);
    void remove(Integer id);
    Optional<FilmShow> findById(Integer filmShowId);
    Optional<FilmShow> findByFilmShowNumber(String filmShowNumber);
}