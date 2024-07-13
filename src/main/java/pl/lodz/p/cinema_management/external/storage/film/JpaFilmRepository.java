package pl.lodz.p.cinema_management.external.storage.film;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFilmRepository extends JpaRepository<FilmEntity, Integer> {
}
