package pl.lodz.p.cinema_management.filmshow.domain;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmShowService {

    private final FilmShowRepository filmShowRepository;

    public FilmShowService(FilmShowRepository filmShowRepository) {
        this.filmShowRepository = filmShowRepository;
    }

    public FilmShow addFilmShow(FilmShow filmShow) {
        return filmShowRepository.save(filmShow);
    }

    public Optional<FilmShow> getFilmShowById(Integer id) {
        return filmShowRepository.findById(id);
    }

    public List<FilmShow> getAllFilmShows() {
        return filmShowRepository.findAll();
    }

    public FilmShow updateFilmShow(FilmShow filmShow) {
        return filmShowRepository.update(filmShow);
    }

    public void deleteFilmShow(Integer id) {
        filmShowRepository.delete(id);
    }

}
