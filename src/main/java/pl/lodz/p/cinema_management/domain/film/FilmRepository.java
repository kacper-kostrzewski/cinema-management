package pl.lodz.p.cinema_management.domain.film;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository {
    Film save(Film film);
    Optional<Film> findById(Integer id);
    List<Film> findAll();
    Film update(Film film);
    void delete(Integer id);
}
