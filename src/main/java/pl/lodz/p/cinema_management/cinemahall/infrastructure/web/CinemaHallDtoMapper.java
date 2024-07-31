package pl.lodz.p.cinema_management.cinemahall.infrastructure.web;

import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CinemaHallDtoMapper {
    CinemaHallDto toDto(CinemaHall cinemaHall);
    CinemaHall toDomain(CinemaHallDto cinemaHallDto);
}
