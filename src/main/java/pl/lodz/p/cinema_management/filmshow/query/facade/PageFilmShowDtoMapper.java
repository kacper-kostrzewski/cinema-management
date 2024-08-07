package pl.lodz.p.cinema_management.filmshow.query.facade;

import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShow;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageFilmShowDtoMapper {

    @Mapping(target = "filmShows", qualifiedByName = "toFilmShowDtoList")
    PageFilmShowDto toPageDto(PageFilmShow domain);

    @Named("toFilmShowDtoList")
    @IterableMapping(qualifiedByName = "filmShowToFilmShowDto")
    List<FilmShowDto> toListDto(List<FilmShow> filmShows);

    @Named("filmShowToFilmShowDto")
    FilmShowDto toDto(FilmShow domain);
}