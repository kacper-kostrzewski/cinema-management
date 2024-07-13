package pl.lodz.p.cinema_management.external.storage.cinemahall;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.domain.cinemahall.CinemaHall;

@Mapper(componentModel = "spring")
public interface CinemaHallEntityMapper {
    CinemaHallEntity toEntity(CinemaHall cinemaHall);
    CinemaHall toDomain(CinemaHallEntity cinemaHallEntity);
}
