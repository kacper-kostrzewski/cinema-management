package pl.lodz.p.cinema_management.reservation.infrastructure.web.reservation;

public record CreateCommand(
        String reservationNumber,
        Integer amountOfSeats
) {}
