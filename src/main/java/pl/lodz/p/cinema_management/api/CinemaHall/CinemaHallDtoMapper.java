package pl.lodz.p.cinema_management.api.CinemaHall;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.domain.CinemaHall.CinemaHall;

@Mapper(componentModel = "spring")
public interface CinemaHallDtoMapper {
    CinemaHallDto toDto(CinemaHall cinemaHall);
    CinemaHall toDomain(CinemaHallDto cinemaHallDto);
}
