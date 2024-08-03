package pl.lodz.p.cinema_management.reservation.api;

import pl.lodz.p.cinema_management.reservation.command.application.*;
import pl.lodz.p.cinema_management.reservation.query.facade.PageReservationDto;
import pl.lodz.p.cinema_management.reservation.query.facade.ReservationDto;
import pl.lodz.p.cinema_management.reservation.query.facade.ReservationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/reservations",
        produces = "application/json",
        consumes = "application/json"
)
class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody CreateUsingCinemaHallCommand createCommand){
        reservationService.create(createCommand);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{reservationNumber}")
    public ResponseEntity<Void> removeReservation(@PathVariable String reservationNumber){
        reservationService.removeByReservationNumber(reservationNumber);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{reservationNumber}/book")
    public ResponseEntity<Void> bookSeats(@PathVariable String reservationNumber, @RequestBody BookCommand bookCommand){
        reservationService.bookSeats(reservationNumber, bookCommand);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{reservationNumber}/release")
    public ResponseEntity<Void> releaseSeats(@PathVariable String reservationNumber, @RequestBody ReleaseCommand releaseCommand){
        reservationService.releaseSeats(reservationNumber, releaseCommand);
        return ResponseEntity.ok().build();
    }

    private final ReservationFacade reservationFacade;

    @GetMapping( path = "/{reservationNumber}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable String reservationNumber) {
        return ResponseEntity.ok(reservationFacade.findByReservationNumber(reservationNumber));
    }

    @GetMapping
    public ResponseEntity<PageReservationDto> getReservations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(reservationFacade.findAll(pageable));
    }

}
