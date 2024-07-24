package pl.lodz.p.cinema_management.filmshow.application.seat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.filmshow.domain.seat.SeatService;


@RestController
@RequestMapping("/api/film-shows/{filmShowId}/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping("/seats/status")
    public ResponseEntity<Void> changeSeatStatus(@RequestBody SeatStatusChangeRequest seatStatusChangeRequest) {
        try {
            seatService.changeSeatStatus(
                    seatStatusChangeRequest.filmShowId(),
                    seatStatusChangeRequest.rowNumber(),
                    seatStatusChangeRequest.seatNumber(),
                    seatStatusChangeRequest.status()
            );
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // HTTP 404 Not Found
        }
    }

}
