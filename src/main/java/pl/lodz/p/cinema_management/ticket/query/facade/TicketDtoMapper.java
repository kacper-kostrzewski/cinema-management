package pl.lodz.p.cinema_management.ticket.query.facade;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.cinema_management.ticket.command.domain.Ticket;
import pl.lodz.p.cinema_management.ticket.command.domain.TicketStatus;

@Mapper(componentModel = "spring")
public interface TicketDtoMapper {

    @Mapping(target = "ticketNumber", source = "ticketNumber.ticketNumber")
    @Mapping(target = "userId", source = "userId.userId")
    @Mapping(target = "filmName", source = "filmName.filmName")
    @Mapping(target = "cinemaHallName", source = "cinemaHallName.cinemaHallName")
    @Mapping(target = "filmShowDateTime", source = "filmShowDateTime.filmShowDateTime")
    @Mapping(target = "seatId", source = "seatId.seatId")
    @Mapping(target = "price", source = "price.amount")
    @Mapping(target = "generationTime", source = "generationTime.generatedAt")
    @Mapping(target = "status", source = "ticketStatus")
    @Mapping(target = "version", source = "version")
    TicketDto toDto(Ticket ticket);

    @Mapping(target = "ticketNumber.ticketNumber", source = "ticketNumber")
    @Mapping(target = "userId.userId", source = "userId")
    @Mapping(target = "filmName.filmName", source = "filmName")
    @Mapping(target = "cinemaHallName.cinemaHallName", source = "cinemaHallName")
    @Mapping(target = "filmShowDateTime.filmShowDateTime", source = "filmShowDateTime")
    @Mapping(target = "seatId.seatId", source = "seatId")
    @Mapping(target = "price.amount", source = "price")
    @Mapping(target = "generationTime.generatedAt", source = "generationTime")
    @Mapping(target = "ticketStatus", source = "status")
    @Mapping(target = "version", source = "version")
    Ticket toDomain(TicketDto ticketDto);

    default String map(TicketStatus status) {
        return status != null ? status.name() : null;
    }

    default TicketStatus map(String status) {
        return status != null ? TicketStatus.valueOf(status) : null;
    }

}
