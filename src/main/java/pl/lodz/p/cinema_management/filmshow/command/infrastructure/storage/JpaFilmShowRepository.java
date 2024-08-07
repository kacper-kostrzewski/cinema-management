package pl.lodz.p.cinema_management.filmshow.command.infrastructure.storage;

import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShow;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface JpaFilmShowRepository extends JpaRepository<FilmShow, Integer> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<FilmShow> findByFilmShowNumber(String filmShowNumber);
}
