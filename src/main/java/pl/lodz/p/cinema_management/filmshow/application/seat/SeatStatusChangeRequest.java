package pl.lodz.p.cinema_management.filmshow.application.seat;

import pl.lodz.p.cinema_management.filmshow.domain.seat.SeatStatus;

public record SeatStatusChangeRequest(
        Integer filmShowId,
        Integer rowNumber,
        Integer seatNumber,
        SeatStatus status
) {
}
