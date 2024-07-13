package pl.lodz.p.cinema_management.external.storage.film;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.domain.film.Film;

@Mapper(componentModel = "spring")
public interface FilmEntityMapper {
    FilmEntity toEntity(Film film);
    Film toDomain(FilmEntity filmEntity);
}

