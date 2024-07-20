package pl.lodz.p.cinema_management.filmshow.application.cinemahall;

public record SeatDto(
        Integer rowNumber,
        Integer seatNumber,
        String seatStatus
) {
}
