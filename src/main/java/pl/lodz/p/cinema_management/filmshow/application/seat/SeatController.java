package pl.lodz.p.cinema_management.filmshow.application.seat;

import org.springframework.web.bind.annotation.*;
import pl.lodz.p.cinema_management.filmshow.domain.seat.SeatService;


@RestController
@RequestMapping("/api/film-shows/{filmShowId}/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

}
