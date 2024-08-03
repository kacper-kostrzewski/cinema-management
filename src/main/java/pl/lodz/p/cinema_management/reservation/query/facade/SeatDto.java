package pl.lodz.p.cinema_management.reservation.query.facade;

public record SeatDto(
        String seatIdentifier,
        Integer takenBy
) {}
