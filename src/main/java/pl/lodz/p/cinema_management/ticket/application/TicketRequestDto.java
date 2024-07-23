package pl.lodz.p.cinema_management.ticket.application;

public record TicketRequestDto(
        Integer filmShowId,
        Integer seatId,
        Integer userId
) {
}
