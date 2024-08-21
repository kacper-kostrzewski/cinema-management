package pl.lodz.p.cinema_management.ticket.query.facade;

import lombok.Value;
import pl.lodz.p.cinema_management.ticket.command.domain.Ticket;

import java.io.Serializable;
import java.util.List;

@Value
public class PageTicket implements Serializable {

    List<Ticket> tickets;
    Integer currentPage;
    Integer totalPages;
    Long totalElements;

}
