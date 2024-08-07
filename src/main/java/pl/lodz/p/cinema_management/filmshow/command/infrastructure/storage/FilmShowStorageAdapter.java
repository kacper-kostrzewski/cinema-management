package pl.lodz.p.cinema_management.filmshow.command.infrastructure.storage;


import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShow;
import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShowAlreadyExistsException;
import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@RequiredArgsConstructor
@Log
public
class FilmShowStorageAdapter implements FilmShowRepository {

    private final JpaFilmShowRepository filmShowRepository;

    @Override
    public FilmShow save(final FilmShow filmShow) {
        try {
            FilmShow saved = filmShowRepository.save(filmShow);
            log.info("Saved entity " + saved);
            return saved;
        } catch (DataIntegrityViolationException ex) {
            log.warning("Film show with number " + filmShow.getFilmShowNumber() + " already exits in db");
            throw new FilmShowAlreadyExistsException();
        }
    }

    @Override
    public void remove(final Integer id) {
        filmShowRepository.findById(id).ifPresent(userEntity -> filmShowRepository.deleteById(id));
    }

    @Override
    public Optional<FilmShow> findByFilmShowNumber(final String filmShowNumber) {
        return filmShowRepository.findByFilmShowNumber(filmShowNumber);
    }

}
