package pl.lodz.p.cinema_management.filmshow.api;

import pl.lodz.p.cinema_management.filmshow.command.application.*;
import pl.lodz.p.cinema_management.filmshow.query.facade.PageFilmShowDto;
import pl.lodz.p.cinema_management.filmshow.query.facade.FilmShowDto;
import pl.lodz.p.cinema_management.filmshow.query.facade.FilmShowFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/filmshows",
        produces = "application/json",
        consumes = "application/json"
)
class FilmShowController {

    private final FilmShowService filmShowService;

    @PostMapping
    public ResponseEntity<Void> createFilmShow(@RequestBody CreateCommand createCommand){
        filmShowService.create(createCommand);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{filmShowNumber}")
    public ResponseEntity<Void> removeFilmShow(@PathVariable String filmShowNumber){
        filmShowService.removeByFilmShowNumber(filmShowNumber);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{filmShowNumber}/book")
    public ResponseEntity<Void> bookSeats(@PathVariable String filmShowNumber, @RequestBody BookCommand bookCommand){
        filmShowService.bookSeats(filmShowNumber, bookCommand);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{filmShowNumber}/release")
    public ResponseEntity<Void> releaseSeats(@PathVariable String filmShowNumber, @RequestBody ReleaseCommand releaseCommand){
        filmShowService.releaseSeats(filmShowNumber, releaseCommand);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{filmShowNumber}/order")
    public ResponseEntity<Void> generateOrder(@PathVariable String filmShowNumber) {
        filmShowService.generateOrder(filmShowNumber);
        return ResponseEntity.ok().build();
    }

    private final FilmShowFacade filmShowFacade;

    @GetMapping( path = "/{filmShowNumber}")
    public ResponseEntity<FilmShowDto> getFilmShow(@PathVariable String filmShowNumber) {
        return ResponseEntity.ok(filmShowFacade.findByFilmShowNumber(filmShowNumber));
    }

    @GetMapping
    public ResponseEntity<PageFilmShowDto> getFilmShows(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(filmShowFacade.findAll(pageable));
    }

}
