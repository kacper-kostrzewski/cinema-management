package pl.lodz.p.cinema_management.filmshow.application.cinemahall;

import pl.lodz.p.cinema_management.filmshow.domain.cinemahall.CinemaHall;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CinemaHallDtoMapper {

    @Mapping(target = "seats", source = "seats")
    CinemaHallDto toDto(CinemaHall cinemaHall);

    @Mapping(target = "seats", source = "seats")
    CinemaHall toDomain(CinemaHallDto cinemaHallDto);

}
