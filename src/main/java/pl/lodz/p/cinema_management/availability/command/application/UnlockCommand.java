package pl.lodz.p.cinema_management.availability.command.application;

public record UnlockCommand(
        String cinemaHallName,
        String reservationNumber
) {
}
