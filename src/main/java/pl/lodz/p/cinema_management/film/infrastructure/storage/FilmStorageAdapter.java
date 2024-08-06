package pl.lodz.p.cinema_management.film.infrastructure.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.film.domain.Film;
import pl.lodz.p.cinema_management.film.domain.FilmAlreadyExistsException;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Log
@Component
public class FilmStorageAdapter implements FilmRepository {

    private final JpaFilmRepository filmRepository;

    @Override
    public Film save(final Film film) {
        try {
            Film saved = filmRepository.save(film);
            log.info("Saved entity " + saved);
            return saved;
        } catch (DataIntegrityViolationException ex) {
            log.warning("Film " + film.getName() + " already exits in db");
            throw new FilmAlreadyExistsException();
        }
    }

    @Override
    public void update(final Film film) {
        filmRepository.findById(film.getId()).ifPresent(filmEntity -> filmRepository.save(film));
    }

    @Override
    public void remove(final Integer id) {
        filmRepository.findById(id).ifPresent(filmEntity -> filmRepository.deleteById(id));
    }

    @Override
    public Optional<Film> findByName(String name) {
        return filmRepository.findByName(name);
    }

    @Override
    public Optional<Film> findById(final Integer id) {
        return filmRepository.findById(id);
    }

    @Override
    public List<Film> findAll() {
        return filmRepository.findAll();
    }
}
