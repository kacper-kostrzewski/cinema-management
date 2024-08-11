package pl.lodz.p.cinema_management.ticket.command.application;

import pl.lodz.p.cinema_management.ticket.command.domain.FilmShow;

public interface FilmShowDetailsService {
    FilmShow getFilmShowById(Integer id);
}