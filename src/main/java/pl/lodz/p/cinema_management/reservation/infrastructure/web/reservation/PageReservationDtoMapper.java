package pl.lodz.p.cinema_management.reservation.infrastructure.web.reservation;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.lodz.p.cinema_management.reservation.domain.PageReservation;
import pl.lodz.p.cinema_management.reservation.domain.Reservation;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageReservationDtoMapper {

    @Mapping(target = "reservations", qualifiedByName = "toReservationDtoList")
    PageReservationDto toPageDto(PageReservation domain);

    @Named("toReservationDtoList")
    @IterableMapping(qualifiedByName = "reservationToReservationDto")
    List<ReservationDto> toListDto(List<Reservation> reservations);

    @Named("reservationToReservationDto")
    ReservationDto toDto(Reservation domain);
}