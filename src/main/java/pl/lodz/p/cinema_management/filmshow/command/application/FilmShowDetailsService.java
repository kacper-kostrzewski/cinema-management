package pl.lodz.p.cinema_management.filmshow.command.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.cinema_management.filmshow.command.domain.*;

@Service
@Transactional
@RequiredArgsConstructor
@Log
public class FilmShowDetailsService {

    private final FilmShowRepository filmShowRepository;

    public FilmShow findByFilmShowId(Integer id) {
        return filmShowRepository.findById(id)
                .orElseThrow(FilmShowNotFoundException::new);
    }

}