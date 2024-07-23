package pl.lodz.p.cinema_management.ticket.application;

import pl.lodz.p.cinema_management.ticket.domain.TicketStatus;

public record SimplifiedTicketDto(
        Integer ticketNumber,
        String filmTitle,
        Integer filmDuration,
        String cinemaHallName,
        String filmDate,
        String filmTime,
        Integer seatRowNumber,
        Integer seatNumber,
        String userEmail,
        TicketStatus ticketStatus
) {
}
