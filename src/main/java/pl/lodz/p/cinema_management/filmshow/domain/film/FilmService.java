package pl.lodz.p.cinema_management.filmshow.domain.film;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film addFilm(Film film) {
        return filmRepository.save(film);
    }

    public Optional<Film> getFilmById(Integer id) {
        return filmRepository.findById(id);
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Film updateFilm(Film film) {
        return filmRepository.update(film);
    }

    public void deleteFilm(Integer id) {
        filmRepository.delete(id);
    }

}
