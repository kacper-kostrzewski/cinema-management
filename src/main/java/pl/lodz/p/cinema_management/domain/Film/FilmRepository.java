package pl.lodz.p.cinema_management.domain.Film;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository {
    Film save(Film film);
    Optional<Film> findById(Integer id);
    List<Film> findAll();
    Film update(Integer id, Film film);
    void delete(Integer id);
}
