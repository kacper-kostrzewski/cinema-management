package pl.lodz.p.cinema_management.ticket.query.facade;

import java.util.List;


public record PageTicketDto(
    List<TicketDto> tickets,
    Integer currentPage,
    Integer totalPages,
    Long totalElements
) {
}
