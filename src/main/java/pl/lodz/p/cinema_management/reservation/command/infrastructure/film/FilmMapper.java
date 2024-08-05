package pl.lodz.p.cinema_management.reservation.command.infrastructure.film;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.reservation.command.domain.Film;

@Mapper(componentModel = "spring")
public interface FilmMapper {
    Film toReservationContext(pl.lodz.p.cinema_management.film.domain.Film film);
}
