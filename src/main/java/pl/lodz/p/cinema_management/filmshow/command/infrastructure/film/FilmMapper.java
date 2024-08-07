package pl.lodz.p.cinema_management.filmshow.command.infrastructure.film;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.filmshow.command.domain.Film;

@Mapper(componentModel = "spring")
public interface FilmMapper {
    Film toReservationContext(pl.lodz.p.cinema_management.film.domain.Film film);
}
