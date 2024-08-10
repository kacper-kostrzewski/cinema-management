package pl.lodz.p.cinema_management.filmshow.command.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShow;
import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShowNotFoundException;
import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShowRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Log
public class SeatConfirmationService {

    private final FilmShowRepository filmShowRepository;

    public void confirmSeats(final Integer filmShowId, final ConfirmCommand confirmCommand) {
        FilmShow filmShow = filmShowRepository.findById(filmShowId).
                orElseThrow(FilmShowNotFoundException::new);
        filmShow.confirmSeats(confirmCommand.userId(), confirmCommand.seatsIdentifiers());
    }

}
