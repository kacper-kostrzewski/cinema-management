package pl.lodz.p.cinema_management.external.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.api.film.FilmDto;
import pl.lodz.p.cinema_management.api.film.FilmDtoMapper;
import pl.lodz.p.cinema_management.domain.film.Film;
import pl.lodz.p.cinema_management.domain.film.FilmNotFoundException;
import pl.lodz.p.cinema_management.domain.film.FilmService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/film")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    private final FilmDtoMapper filmDtoMapper;

    @PostMapping
    ResponseEntity<FilmDto> addFilm(@RequestBody FilmDto filmDto) {
        Film film = filmService.addFilm(filmDtoMapper.toDomain(filmDto));
        return ResponseEntity.ok(filmDtoMapper.toDto(film));
    }

    @GetMapping
    ResponseEntity<List<FilmDto>> getAllFilms() {
        return ResponseEntity.ok(filmService.getAllFilms().stream().map(filmDtoMapper::toDto).toList());
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<FilmDto> getFilmById(@PathVariable Integer id) {
        Optional<Film> film = filmService.getFilmById(id);
        if(film.isPresent()) {
            return ResponseEntity.ok(filmDtoMapper.toDto(film.get()));
        } else return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<FilmDto> updateFilm(@RequestBody FilmDto filmDto) {
        try {
            Film updatedFilm = filmService.updateFilm(filmDtoMapper.toDomain(filmDto));
            return ResponseEntity.ok(filmDtoMapper.toDto(updatedFilm));
        } catch (FilmNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    void deleteFilm(@PathVariable Integer id) {
        filmService.deleteFilm(id);
    }

}
