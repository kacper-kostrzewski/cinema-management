package pl.lodz.p.cinema_management.external.storage.film;

import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.api.film.FilmDtoMapper;
import pl.lodz.p.cinema_management.domain.film.Film;
import pl.lodz.p.cinema_management.domain.film.FilmRepository;

import java.util.*;

@Repository
public class FilmMemoryStorageAdapter implements FilmRepository {
    private final FilmDtoMapper filmDtoMapper;
    private final Map<Integer, Film> films = new HashMap<>();
    private static Integer nextId = 0;

    public FilmMemoryStorageAdapter(FilmDtoMapper filmDtoMapper) {
        this.filmDtoMapper = filmDtoMapper;
    }

    @Override
    public Film save(Film film) {
        nextId++;
        films.put(nextId, film);
        film.setId(nextId);
        return film;
    }

    @Override
    public Optional<Film> findById(Integer id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film update(Integer id, Film film) {
        film.setId(id);
        films.put(id, film);
        return film;
    }

    @Override
    public void delete(Integer id) {
        films.remove(id);
    }
}
