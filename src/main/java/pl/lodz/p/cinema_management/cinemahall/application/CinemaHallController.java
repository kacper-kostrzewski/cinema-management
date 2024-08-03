package pl.lodz.p.cinema_management.cinemahall.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHallService;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/cinema_halls",
        produces = "application/json",
        consumes = "application/json"
)
class CinemaHallController {

    private final CinemaHallService cinemaHallService;
    private final CinemaHallDtoMapper cinemaHallDtoMapper;

    @GetMapping( path = "/{id}")
    public ResponseEntity<CinemaHallDto> getCinemaHall(@PathVariable Integer id) {
        CinemaHall cinemaHall = cinemaHallService.findById(id);
        return ResponseEntity.ok(cinemaHallDtoMapper.toDto(cinemaHall));
    }

    @GetMapping
    public ResponseEntity<List<CinemaHallDto>> getAllCinemaHalls() {
        return ResponseEntity.ok(cinemaHallService.getAllCinemaHalls().stream().map(cinemaHallDtoMapper::toDto).toList());
    }

    @PostMapping
    public ResponseEntity<CinemaHallDto> saveCinemaHall(@RequestBody CinemaHallDto dto) {
        CinemaHall cinemaHall = cinemaHallService.save(cinemaHallDtoMapper.toDomain(dto));
        return ResponseEntity.ok(cinemaHallDtoMapper.toDto(cinemaHall));
    }

    @PutMapping
    public ResponseEntity<Void> updateCinemaHall(@RequestBody CinemaHallDto dto) {
        cinemaHallService.update(cinemaHallDtoMapper.toDomain(dto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeCinemaHall(@PathVariable Integer id){
        cinemaHallService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
