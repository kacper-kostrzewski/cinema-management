package pl.lodz.p.cinema_management.cinemahall.application;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHall;

@Mapper(componentModel = "spring")
public interface CinemaHallDtoMapper {

    CinemaHallDto toDto(CinemaHall domain);
    CinemaHall toDomain(CinemaHallDto dto);

}