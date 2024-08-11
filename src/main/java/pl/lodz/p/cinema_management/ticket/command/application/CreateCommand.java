package pl.lodz.p.cinema_management.ticket.command.application;

import java.math.BigDecimal;

public record CreateCommand(
        String ticketNumber,
        Integer filmShowId,
        Integer userId,
        String seatId,
        BigDecimal price
) {
}
