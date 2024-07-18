package pl.lodz.p.cinema_management.filmshow.infrastructure;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShow;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowNotFoundException;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class FilmShowDatabaseStorageAdapter implements FilmShowRepository {

    private final JpaFilmShowRepository jpaFilmShowRepository;

    public FilmShowDatabaseStorageAdapter(JpaFilmShowRepository jpaFilmShowRepository) {
        this.jpaFilmShowRepository = jpaFilmShowRepository;
    }

    @Override
    public FilmShow save(FilmShow filmShow) {
        return jpaFilmShowRepository.save(filmShow);
    }

    @Override
    public Optional<FilmShow> findById(Integer id) {
        return jpaFilmShowRepository.findById(id);
    }

    @Override
    public List<FilmShow> findAll() {
        return jpaFilmShowRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public FilmShow update(FilmShow filmShow) {
        Optional<FilmShow> filmShowToUpdate = jpaFilmShowRepository.findById(filmShow.getId());
        if (filmShowToUpdate.isPresent()) {
            return jpaFilmShowRepository.save(filmShow);
        }
        throw new FilmShowNotFoundException();
    }

    @Override
    public void delete(Integer id) {
        jpaFilmShowRepository.deleteById(id);
    }

}
