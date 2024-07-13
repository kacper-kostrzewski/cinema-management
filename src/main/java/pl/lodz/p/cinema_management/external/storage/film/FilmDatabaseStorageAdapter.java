package pl.lodz.p.cinema_management.external.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.domain.film.Film;
import pl.lodz.p.cinema_management.domain.film.FilmNotFoundException;
import pl.lodz.p.cinema_management.domain.film.FilmRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FilmDatabaseStorageAdapter implements FilmRepository {
    private final JpaFilmRepository jpaFilmRepository;
    private final FilmEntityMapper filmEntityMapper;

    @Override
    public Film save(Film film) {
        FilmEntity filmEntity = filmEntityMapper.toEntity(film);
        return filmEntityMapper.toDomain(jpaFilmRepository.save(filmEntity));
    }

    @Override
    public Optional<Film> findById(Integer id) {
        return jpaFilmRepository.findById(id).map(filmEntityMapper::toDomain);
    }

    @Override
    public List<Film> findAll() {
        return jpaFilmRepository.findAll(Sort.by(Sort.Direction.ASC,"id")).stream().map(filmEntityMapper::toDomain).toList();
    }

    @Override
    public void delete(Integer id) {
        jpaFilmRepository.deleteById(id);
    }

    @Override
    public Film update(Film film) {
        Optional<FilmEntity> filmEntity = jpaFilmRepository.findById(film.getId());
        if (filmEntity.isPresent()) {
            FilmEntity tempFilmEntity = filmEntityMapper.toEntity(film);
            return filmEntityMapper.toDomain(jpaFilmRepository.save(tempFilmEntity));
        }
        throw new FilmNotFoundException();
    }

}
