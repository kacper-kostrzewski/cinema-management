package pl.lodz.p.cinema_management.ticket.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.lodz.p.cinema_management.ticket.domain.Ticket;

@Mapper(componentModel = "spring")
public interface TicketDtoMapper {

    @Mapping(source = "filmShowId", target = "filmShow.id")
    @Mapping(source = "seatId", target = "seat.id")
    @Mapping(source = "userId", target = "user.id")
    Ticket toDomain(TicketRequestDto dto);

    @Mapping(source = "filmShow", target = "filmShow")
    @Mapping(source = "seat", target = "seat")
    @Mapping(source = "user", target = "user")
    TicketResponseDto toResponseDto(Ticket entity);

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
