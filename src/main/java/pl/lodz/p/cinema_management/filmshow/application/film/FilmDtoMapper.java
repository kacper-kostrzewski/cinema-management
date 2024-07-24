package pl.lodz.p.cinema_management.filmshow.application.film;

import pl.lodz.p.cinema_management.filmshow.domain.film.Film;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmDtoMapper {
    FilmDto toDto(Film film);
    Film toDomain(FilmDto filmDto);
}
