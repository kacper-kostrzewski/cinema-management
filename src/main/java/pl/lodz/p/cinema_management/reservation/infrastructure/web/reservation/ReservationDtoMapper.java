package pl.lodz.p.cinema_management.reservation.infrastructure.web.reservation;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.reservation.domain.Reservation;

@Mapper(componentModel = "spring")
public interface ReservationDtoMapper {

    ReservationDto toDto(Reservation domain);

}