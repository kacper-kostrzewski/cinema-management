package pl.lodz.p.cinema_management.ticket.query.facade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TicketDto(
        Integer id,
        String ticketNumber,
        Integer userId,
        String filmName,
        String cinemaHallName,
        LocalDateTime filmShowDateTime,
        String seatId,
        BigDecimal price,
        LocalDateTime generationTime,
        String status,
        Integer version
) {
}
