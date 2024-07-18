package pl.lodz.p.cinema_management.filmshow.infrastructure.film;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.filmshow.domain.film.Film;
import pl.lodz.p.cinema_management.filmshow.domain.film.FilmNotFoundException;
import pl.lodz.p.cinema_management.filmshow.domain.film.FilmRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class FilmDatabaseStorageAdapter implements FilmRepository {

    private final JpaFilmRepository jpaFilmRepository;

    public FilmDatabaseStorageAdapter(JpaFilmRepository jpaFilmRepository) {
        this.jpaFilmRepository = jpaFilmRepository;
    }

    @Override
    public Film save(Film film) {
        return jpaFilmRepository.save(film);
    }

    @Override
    public Optional<Film> findById(Integer id) {
        return jpaFilmRepository.findById(id);
    }

    @Override
    public List<Film> findAll() {
        return jpaFilmRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Film update(Film film) {
        Optional<Film> filmToUpdate = jpaFilmRepository.findById(film.getId());
        if (filmToUpdate.isPresent()) {
            return jpaFilmRepository.save(film);
        }
        throw new FilmNotFoundException();
    }

    @Override
    public void delete(Integer id) {
        jpaFilmRepository.deleteById(id);
    }

}
