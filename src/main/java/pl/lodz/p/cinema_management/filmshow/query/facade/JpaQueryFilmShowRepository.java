package pl.lodz.p.cinema_management.filmshow.query.facade;

import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaQueryFilmShowRepository extends JpaRepository<FilmShow, Integer> {
    Optional<FilmShow> findByFilmShowNumber(String filmShowNumber);
}
