package pl.lodz.p.cinema_management.reservation.command.application;

public record CreateCommand(
        String reservationNumber,
        Integer amountOfSeats
) {}
