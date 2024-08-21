package pl.lodz.p.cinema_management.ticket.command.domain;

public record TicketNumber(String ticketNumber) {

    public static TicketNumber of(String ticketNumber) {
        return new TicketNumber(ticketNumber);
    }

    public String asString() {
        return ticketNumber;
    }

}
