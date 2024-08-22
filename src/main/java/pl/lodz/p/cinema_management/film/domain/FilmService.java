package pl.lodz.p.cinema_management.film.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.cinema_management.film.infrastructure.storage.FilmRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log
public class FilmService {

    private final FilmRepository filmRepository;

    public Film save(Film film) {
        log.info("Attempting to save Film: " + film.toString());
        Film savedFilm = filmRepository.save(film);
        log.info("Film saved successfully: " + savedFilm.toString());
        return savedFilm;
    }

    public void update(Film film) {
        log.info("Attempting to update Film with ID: " + film.getId());
        filmRepository.update(film);
        log.info("Film updated successfully with ID: " + film.getId());
    }

    public void removeById(Integer id) {
        log.info("Attempting to remove Film with ID: " + id);
        filmRepository.remove(id);
        log.info("Film removed successfully with ID: " + id);
    }

    public Film findByName(String name) {
        log.info("Searching for Film with name: " + name);
        Film film = filmRepository.findByName(name)
                .orElseThrow(() -> {
                    log.warning("Film not found with name: " + name);
                    return new FilmNotFoundException();
                });
        log.info("Film found: " + film.toString());
        return film;
    }

    public Film findById(Integer id) {
        log.info("Searching for Film with ID: " + id);
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> {
                    log.warning("Film not found with ID: " + id);
                    return new FilmNotFoundException();
                });
        log.info("Film found: " + film.toString());
        return film;
    }

    public List<Film> getAllFilms() {
        log.info("Retrieving all Films");
        List<Film> films = filmRepository.findAll();
        log.info("Total Films found: " + films.size());
        return films;
    }

}
