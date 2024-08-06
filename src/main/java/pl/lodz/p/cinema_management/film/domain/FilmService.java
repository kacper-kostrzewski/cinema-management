package pl.lodz.p.cinema_management.film.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.cinema_management.film.infrastructure.storage.FilmRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;

    public Film save(Film film) {
        return filmRepository.save(film);
    }

    public void update(Film film) {
        filmRepository.update(film);
    }

    public void removeById(Integer id) {
        filmRepository.remove(id);
    }

    public Film findByName(String name) {
        return filmRepository.findByName(name)
                .orElseThrow(FilmNotFoundException::new);
    }

    public Film findById(Integer id) {
        return filmRepository.findById(id)
                .orElseThrow(FilmNotFoundException::new);
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

}
