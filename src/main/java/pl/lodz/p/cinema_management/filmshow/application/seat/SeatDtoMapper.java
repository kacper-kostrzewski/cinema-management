package pl.lodz.p.cinema_management.filmshow.application.seat;

import pl.lodz.p.cinema_management.filmshow.domain.seat.Seat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeatDtoMapper {
    SeatDto toDto(Seat seat);
    Seat toDomain(SeatDto seatDto);
}
