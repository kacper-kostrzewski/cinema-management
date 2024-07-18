package pl.lodz.p.cinema_management.filmshow.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShow;

public interface JpaFilmShowRepository extends JpaRepository<FilmShow, Integer> {
}
