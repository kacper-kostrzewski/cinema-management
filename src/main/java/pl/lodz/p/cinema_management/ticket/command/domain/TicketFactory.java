package pl.lodz.p.cinema_management.ticket.command.domain;

public class TicketFactory {

    public static Ticket createTicket(TicketNumber ticketNumber, UserId userId, FilmName filmName, CinemaHallName cinemaHallName, FilmShowDateTime filmShowDateTime, SeatId seatId, Price price, GenerationTime generationTime, TicketStatus ticketStatus) {
        return new Ticket(ticketNumber, userId, filmName, cinemaHallName, filmShowDateTime, seatId, price, generationTime, ticketStatus);
    }

}
