package pl.lodz.p.cinema_management.filmshow.application;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pl.lodz.p.cinema_management.filmshow.domain.FilmShow;

@Mapper(componentModel = "spring")
public interface FilmShowDtoMapper {

    @Mapping(source = "film.id", target = "filmId")
    @Mapping(source = "cinemaHall.id", target = "cinemaHallId")
    CreateFilmShowDto toResponseDto(FilmShow filmShow);

    @Mapping(source = "filmId", target = "film.id")
    @Mapping(source = "cinemaHallId", target = "cinemaHall.id")
    FilmShow toDomain(CreateFilmShowDto createFIlmShowDto);

    FilmShowDto toDto(FilmShow filmShow);
}
