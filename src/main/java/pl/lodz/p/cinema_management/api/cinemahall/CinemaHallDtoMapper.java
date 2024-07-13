package pl.lodz.p.cinema_management.api.cinemahall;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.domain.cinemahall.CinemaHall;

@Mapper(componentModel = "spring")
public interface CinemaHallDtoMapper {
    CinemaHallDto toDto(CinemaHall cinemaHall);
    CinemaHall toDomain(CinemaHallDto cinemaHallDto);
}
