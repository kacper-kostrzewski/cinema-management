package pl.lodz.p.cinema_management.reservation.infrastructure.web.reservation;

public record SeatDto(
        Integer seatNumber,
        Integer takenBy
) {}
