package pl.lodz.p.cinema_management.cinemahall.infrastructure.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHallNotFoundException;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHallService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/cinema_halls")
@RequiredArgsConstructor
public class CinemaHallController {
    private final CinemaHallService cinemaHallService;
    private final CinemaHallDtoMapper cinemaHallDtoMapper;

    @PostMapping
    ResponseEntity<CinemaHallDto> addCinemaHall(@RequestBody CinemaHallDto cinemaHallDto) {
        CinemaHall cinemaHall = cinemaHallService.addCinemaHall(cinemaHallDtoMapper.toDomain(cinemaHallDto));
        return ResponseEntity.ok(cinemaHallDtoMapper.toDto(cinemaHall));
    }

    @GetMapping
    ResponseEntity<List<CinemaHallDto>> getAllCinemaHalls() {
        return ResponseEntity.ok(cinemaHallService.getAllCinemaHalls().stream().map(cinemaHallDtoMapper::toDto).toList());
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<CinemaHallDto> getCinemaHallById(@PathVariable Integer id) {
        Optional<CinemaHall> cinemaHall = cinemaHallService.getCinemaHallById(id);
        return cinemaHall.map(hall -> ResponseEntity.ok(cinemaHallDtoMapper.toDto(hall))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<CinemaHallDto> updateCinemaHall(@RequestBody CinemaHallDto cinemaHallDto) {
        try {
            CinemaHall updatedCinemaHall = cinemaHallService.updateCinemaHall(cinemaHallDtoMapper.toDomain(cinemaHallDto));
            return ResponseEntity.ok(cinemaHallDtoMapper.toDto(updatedCinemaHall));
        } catch (CinemaHallNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    void deleteCinemaHall(@PathVariable Integer id) {
        cinemaHallService.deleteCinemaHall(id);
    }
}
