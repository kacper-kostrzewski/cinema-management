package pl.lodz.p.cinema_management.availability.command.application;

import java.time.LocalDateTime;

public record LockCommand(
        String cinemaHallName,
        String filmShowNumber,
        LocalDateTime lockStart,
        Integer duration
) {
}
