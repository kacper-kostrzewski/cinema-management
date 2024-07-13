package pl.lodz.p.cinema_management.api.film;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.domain.film.Film;

@Mapper(componentModel = "spring")
public interface FilmDtoMapper {
    FilmDto toDto(Film film);
    Film toDomain(FilmDto filmDto);
}
