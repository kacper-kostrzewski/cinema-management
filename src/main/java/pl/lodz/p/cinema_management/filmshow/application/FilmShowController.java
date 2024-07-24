package pl.lodz.p.cinema_management.filmshow.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShow;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowService;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/film_shows")
@RequiredArgsConstructor
public class FilmShowController {
    private final FilmShowService filmShowService;
    private final FilmShowDtoMapper filmShowDtoMapper;

    @PostMapping
    ResponseEntity<AddFilmShowDto> addFilmShow(@RequestBody AddFilmShowDto addFilmShowDto) {
        FilmShow filmShow = filmShowService.addFilmShow(filmShowDtoMapper.toDomain(addFilmShowDto));
        return ResponseEntity.ok(filmShowDtoMapper.addFilmShowDto(filmShow));
    }

    @GetMapping
    ResponseEntity<List<FilmShowDto>> getAllFilmShows() {
        return ResponseEntity.ok(filmShowService.getAllFilmShows().stream().map(filmShowDtoMapper::toDto).toList());
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<FilmShowDto> getFilmShowById(@PathVariable Integer id) {
        Optional<FilmShow> filmShow = filmShowService.getFilmShowById(id);
        return filmShow.map(show -> ResponseEntity.ok(filmShowDtoMapper.toDto(show))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<AddFilmShowDto> updateFilmShow(@RequestBody AddFilmShowDto addFilmShowDto) {
        try {
            FilmShow updatedFilmShow = filmShowService.updateFilmShow(filmShowDtoMapper.toDomain(addFilmShowDto));
            return ResponseEntity.ok(filmShowDtoMapper.addFilmShowDto(updatedFilmShow));
        } catch (FilmShowNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    void deleteFilmShow(@PathVariable Integer id) {
        filmShowService.deleteFilmShow(id);
    }
}
