package pl.lodz.p.cinema_management.availability.command.application;

import java.time.LocalDateTime;

public record LockCommand(
        String cinemaHallName,
        String reservationNumber,
        LocalDateTime lockStart,
        Integer duration
) {
}
