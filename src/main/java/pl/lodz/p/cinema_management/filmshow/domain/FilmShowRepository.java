package pl.lodz.p.cinema_management.filmshow.domain;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmShowRepository {

    FilmShow save(FilmShow filmShow);
    Optional<FilmShow> findById(Integer id);
    List<FilmShow> findAll();
    FilmShow update(FilmShow filmShow);
    void delete(Integer id);

}
