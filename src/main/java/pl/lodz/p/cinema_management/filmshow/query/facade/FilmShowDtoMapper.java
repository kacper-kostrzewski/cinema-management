package pl.lodz.p.cinema_management.filmshow.query.facade;

import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShow;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmShowDtoMapper {
    FilmShowDto toDto(FilmShow domain);
}