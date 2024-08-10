package pl.lodz.p.cinema_management.ticket.command.application;

public record CreateCommand(
        String ticketNumber,
        Integer filmShowId,
        Integer userId,
        String seatId
) {
}
