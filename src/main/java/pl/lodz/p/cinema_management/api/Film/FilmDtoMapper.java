package pl.lodz.p.cinema_management.api.Film;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.domain.Film.Film;

@Mapper(componentModel = "spring")
public interface FilmDtoMapper {
    FilmDto toDto(Film film);
    Film toDomain(FilmDto filmDto);
}
