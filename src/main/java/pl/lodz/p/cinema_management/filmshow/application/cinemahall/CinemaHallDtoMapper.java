package pl.lodz.p.cinema_management.filmshow.application.cinemahall;

import pl.lodz.p.cinema_management.filmshow.domain.cinemahall.CinemaHall;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CinemaHallDtoMapper {
    CinemaHallDto toDto(CinemaHall cinemaHall);
    CinemaHall toDomain(CinemaHallDto cinemaHallDto);
}
