package pl.lodz.p.cinema_management.film.application;

import pl.lodz.p.cinema_management.film.domain.Film;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmDtoMapper {
    FilmDto toDto(Film domain);
    Film toDomain(FilmDto dto);
}