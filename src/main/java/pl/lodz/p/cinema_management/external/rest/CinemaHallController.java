package pl.lodz.p.cinema_management.external.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.api.CinemaHall.CinemaHallDto;
import pl.lodz.p.cinema_management.api.CinemaHall.CinemaHallDtoMapper;
import pl.lodz.p.cinema_management.domain.CinemaHall.CinemaHall;
import pl.lodz.p.cinema_management.domain.CinemaHall.CinemaHallService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cinema-hall")
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
        if(cinemaHall.isPresent()) {
            return ResponseEntity.ok(cinemaHallDtoMapper.toDto(cinemaHall.get()));
        } else return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<CinemaHallDto> updateCinemaHall(@PathVariable Integer id, @RequestBody CinemaHallDto cinemaHallDto) {
        Optional<CinemaHall> cinemaHall = cinemaHallService.getCinemaHallById(id);
        if(!cinemaHall.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            CinemaHall updatedCinemaHall = cinemaHallService.updateCinemaHall(id, cinemaHallDtoMapper.toDomain(cinemaHallDto));
            return ResponseEntity.ok(cinemaHallDtoMapper.toDto(updatedCinemaHall));
        }
    }

    @DeleteMapping(path = "/{id}")
    void deleteCinemaHall(@PathVariable Integer id) {
        cinemaHallService.deleteCinemaHall(id);
    }
}
