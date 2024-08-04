package pl.lodz.p.cinema_management.reservation.command.application;

public record CreateUsingCinemaHallCommand(
        String reservationNumber,
        Integer cinemaHallId
) {}
