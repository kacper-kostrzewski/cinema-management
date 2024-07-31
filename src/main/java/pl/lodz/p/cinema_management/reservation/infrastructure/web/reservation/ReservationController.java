package pl.lodz.p.cinema_management.reservation.infrastructure.web.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.reservation.application.ReservationService;
import pl.lodz.p.cinema_management.reservation.domain.Reservation;
import pl.lodz.p.cinema_management.security.JWTUtil;
import pl.lodz.p.cinema_management.security.Security;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/reservations",
        produces = "application/json",
        consumes = "application/json"
)
class ReservationController {

    private final ReservationService reservationService;
    private final ReservationDtoMapper reservationMapper;
    private final PageReservationDtoMapper pageReservationDtoMapper;
    private final JWTUtil jwtUtil;
    private final Security security;

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody CreateCommand bookCommand){
        reservationService.create(bookCommand.reservationNumber(), bookCommand.amountOfSeats());
        return ResponseEntity.ok().build();
    }

    @GetMapping( path = "/{reservationNumber}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable String reservationNumber) {
        Reservation reservation = reservationService.findByReservationNumberReadOnly(reservationNumber);
        return ResponseEntity
                .ok(reservationMapper.toDto(reservation));
    }

    @GetMapping
    public ResponseEntity<PageReservationDto> getReservations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageReservationDto pageReservations = pageReservationDtoMapper.toPageDto(reservationService.findAll(pageable));

        return ResponseEntity.ok(pageReservations);
    }

    @DeleteMapping("{reservationNumber}")
    public ResponseEntity<Void> removeReservation(@PathVariable String reservationNumber){
        reservationService.removeByReservationNumber(reservationNumber);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{reservationNumber}/book")
    public ResponseEntity<Void> bookSeats(@PathVariable String reservationNumber, @RequestBody BookCommand bookCommand){
        if (bookCommand.userId() != null) {
            reservationService.bookSeatsOnBehalfOfTheUser(bookCommand.userId(), reservationNumber, bookCommand.seatNumbers());
        }else {
            reservationService.bookSeats(reservationNumber, bookCommand.seatNumbers());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("{reservationNumber}/release")
    public ResponseEntity<Void> releaseSeats(@PathVariable String reservationNumber, @RequestBody ReleaseCommand releaseCommand){
        reservationService.releaseSeats(reservationNumber, releaseCommand.seatNumbers());
        return ResponseEntity.ok().build();
    }

}
