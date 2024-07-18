package pl.lodz.p.cinema_management.filmshow.infrastructure.film;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.filmshow.domain.film.Film;

public interface JpaFilmRepository extends JpaRepository<Film, Integer> {
}
