package pl.lodz.p.cinema_management.reservation.query.facade;

import java.util.List;

public record ReservationDto(
        String reservationNumber,
        String cinemaHallName,
        List<SeatDto>seats,
        Integer version
) {}
