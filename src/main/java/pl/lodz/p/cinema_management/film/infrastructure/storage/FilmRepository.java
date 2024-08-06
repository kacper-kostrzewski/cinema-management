package pl.lodz.p.cinema_management.film.infrastructure.storage;

import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.film.domain.Film;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository {
    Film save(Film film);
    void update(Film film);
    void remove(Integer id);
    Optional<Film> findByName(String name);
    Optional<Film> findById(Integer id);
    List<Film> findAll();
}
