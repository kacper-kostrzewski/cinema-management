package pl.lodz.p.cinema_management.ticket.command.infrastructure.storage.filmshow;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.ticket.command.application.FilmShowDetailsService;

@Component
@RequiredArgsConstructor
public class FilmShowDetailsFacade implements FilmShowDetailsService {

    private final pl.lodz.p.cinema_management.filmshow.command.application.FilmShowDetailsService filmShowDetailsService;
    private final FilmShowDetailsMapper filmShowDetailsMapper;

    @Override
    public pl.lodz.p.cinema_management.ticket.command.domain.FilmShow getFilmShowById(Integer id) {
        pl.lodz.p.cinema_management.filmshow.command.domain.FilmShow filmShow = filmShowDetailsService.findByFilmShowId(id);
        return filmShowDetailsMapper.toTicketContext(filmShow);
    }

}
