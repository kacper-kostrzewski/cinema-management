package pl.lodz.p.cinema_management.film.infrastructure.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.film.domain.Film;

import java.util.Optional;

public interface JpaFilmRepository extends JpaRepository<Film, Integer> {
    Optional<Film> findByName(String name);
}
