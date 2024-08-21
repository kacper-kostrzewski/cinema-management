package pl.lodz.p.cinema_management.ticket.command.infrastructure.storage.filmshow;

import org.mapstruct.Mapper;
import pl.lodz.p.cinema_management.ticket.command.domain.FilmShow;

@Mapper(componentModel = "spring")
public interface FilmShowDetailsMapper {
    FilmShow toTicketContext(pl.lodz.p.cinema_management.filmshow.command.domain.FilmShow filmShow);
}
