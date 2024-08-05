package pl.lodz.p.cinema_management.reservation.query.facade;

import java.time.LocalDateTime;
import java.util.List;

public record ReservationDto(
        String reservationNumber,
        String cinemaHallName,
        String filmName,
        LocalDateTime reservationDateTime,
        List<SeatDto>seats,
        Integer version
) {}
