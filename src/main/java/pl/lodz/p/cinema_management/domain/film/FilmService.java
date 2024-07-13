package pl.lodz.p.cinema_management.domain.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;

    public Film addFilm(Film film) {
        return filmRepository.save(film);
    }

    public Optional<Film> getFilmById(Integer id) {
        return filmRepository.findById(id);
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Film updateFilm(Integer id, Film film) {
        return filmRepository.update(id, film);
    }

    public void deleteFilm(Integer id) {
        filmRepository.delete(id);
    }

}
