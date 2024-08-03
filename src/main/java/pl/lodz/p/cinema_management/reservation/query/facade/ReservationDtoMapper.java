package pl.lodz.p.cinema_management.reservation.query.facade;

import pl.lodz.p.cinema_management.reservation.command.domain.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationDtoMapper {

    ReservationDto toDto(Reservation domain);

}