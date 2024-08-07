package pl.lodz.p.cinema_management.filmshow.query.facade;

public record SeatDto(
        String seatIdentifier,
        Integer takenBy
) {}
