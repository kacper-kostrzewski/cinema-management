package pl.lodz.p.cinema_management.film.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.film.domain.Film;
import pl.lodz.p.cinema_management.film.domain.FilmService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/films",
        produces = "application/json",
        consumes = "application/json"
)
class FilmController {

    private final FilmService filmService;
    private final FilmDtoMapper filmDtoMapper;

    @GetMapping( path = "/{id}")
    public ResponseEntity<FilmDto> getFilm(@PathVariable Integer id) {
        Film film = filmService.findById(id);
        return ResponseEntity.ok(filmDtoMapper.toDto(film));
    }

    @GetMapping
    public ResponseEntity<List<FilmDto>> getAllFilms() {
        return ResponseEntity.ok(filmService.getAllFilms().stream().map(filmDtoMapper::toDto).toList());
    }

    @PostMapping
    public ResponseEntity<FilmDto> saveFilm(@RequestBody FilmDto dto) {
        Film film = filmService.save(filmDtoMapper.toDomain(dto));
        return ResponseEntity.ok(filmDtoMapper.toDto(film));
    }

    @PutMapping
    public ResponseEntity<Void> updateFilm(@RequestBody FilmDto dto) {
        filmService.update(filmDtoMapper.toDomain(dto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeFilm(@PathVariable Integer id){
       filmService.removeById(id);
       return ResponseEntity.noContent().build();
    }

}
