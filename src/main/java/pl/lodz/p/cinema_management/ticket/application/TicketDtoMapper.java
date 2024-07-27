package pl.lodz.p.cinema_management.ticket.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.cinema_management.ticket.domain.Ticket;

@Mapper(componentModel = "spring")
public interface TicketDtoMapper {

    @Mapping(source = "filmShowId", target = "filmShow.id")
    @Mapping(source = "rowNumber", target = "seat.rowNumber")
    @Mapping(source = "seatNumber", target = "seat.seatNumber")
    @Mapping(source = "userId", target = "user.id")
    Ticket toDomain(TicketRequestDto dto);

    @Mapping(source = "id", target = "ticketNumber")
    @Mapping(source = "filmShow.film.title", target = "filmTitle")
    @Mapping(source = "filmShow.film.duration", target = "filmDuration")
    @Mapping(source = "filmShow.cinemaHall.name", target = "cinemaHallName")
    @Mapping(source = "filmShow.startTime", target = "filmDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "filmShow.startTime", target = "filmTime", dateFormat = "HH:mm")
    @Mapping(source = "seat.rowNumber", target = "seatRowNumber")
    @Mapping(source = "seat.seatNumber", target = "seatNumber")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "ticketStatus", target = "ticketStatus")
    SimplifiedTicketDto toSimplifiedTicketDto(Ticket ticket);

}
