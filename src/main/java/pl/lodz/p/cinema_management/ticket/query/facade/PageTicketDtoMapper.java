package pl.lodz.p.cinema_management.ticket.query.facade;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.lodz.p.cinema_management.payment.command.domain.Payment;
import pl.lodz.p.cinema_management.payment.command.domain.SeatId;
import pl.lodz.p.cinema_management.ticket.command.domain.Ticket;
import pl.lodz.p.cinema_management.ticket.command.domain.TicketStatus;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PageTicketDtoMapper {

    @Mapping(target = "tickets", source = "tickets", qualifiedByName = "toTicketDtoList")
    PageTicketDto toPageDto(PageTicket pageTicket);

    @Named("toTicketDtoList")
    @IterableMapping(qualifiedByName = "ticketToTicketDto")
    List<TicketDto> toTicketDtoList(List<Ticket> tickets);

    @Named("ticketToTicketDto")
    @Mapping(target = "ticketNumber", source = "ticketNumber.ticketNumber")
    @Mapping(target = "userId", source = "userId.userId")
    @Mapping(target = "filmName", source = "filmName.filmName")
    @Mapping(target = "cinemaHallName", source = "cinemaHallName.cinemaHallName")
    @Mapping(target = "filmShowDateTime", source = "filmShowDateTime.filmShowDateTime")
    @Mapping(target = "seatId", source = "seatId.seatId")
    @Mapping(target = "price", source = "price.amount")
    @Mapping(target = "generationTime", source = "generationTime.generatedAt")
    @Mapping(target = "status", source = "ticketStatus", qualifiedByName = "ticketStatusToString")
    @Mapping(target = "version", source = "version")
    TicketDto toDto(Ticket ticket);

    @Named("ticketStatusToString")
    default String ticketStatusToString(TicketStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("stringToTicketStatus")
    default TicketStatus stringToTicketStatus(String status) {
        return status != null ? TicketStatus.valueOf(status) : null;
    }
}
