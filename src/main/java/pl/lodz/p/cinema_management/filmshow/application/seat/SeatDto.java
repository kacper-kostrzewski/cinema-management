package pl.lodz.p.cinema_management.filmshow.application.seat;

public record SeatDto(
        Integer rowNumber,
        Integer seatNumber,
        String seatStatus
        ) {
}
