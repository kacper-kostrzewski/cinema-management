package pl.lodz.p.cinema_management.filmshow.query.facade;


import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShow;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log
public
class FilmShowFacade {

    private final JpaQueryFilmShowRepository filmShowRepository;
    private final FilmShowDtoMapper filmShowDtoMapper;
    private final PageFilmShowDtoMapper pageFilmShowDtoMapper;

    public FilmShowDto findByFilmShowNumber(final String filmShowNumber) {
        final Optional<FilmShow> maybeFilmShow = filmShowRepository.findByFilmShowNumber(filmShowNumber);
        return filmShowDtoMapper.toDto(maybeFilmShow.orElseThrow(FilmShowDtoNotFoundException::new));
    }

    public PageFilmShowDto findAll(final Pageable pageable) {
        Page<FilmShow> pageOfFilmShowsEntity = filmShowRepository.findAll(pageable);
        List<FilmShow> filmShowsOnCurrentPage = new ArrayList<>(pageOfFilmShowsEntity.getContent());

        final PageFilmShow pageFilmShow = new PageFilmShow(
                filmShowsOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfFilmShowsEntity.getTotalPages(),
                pageOfFilmShowsEntity.getTotalElements()
        );
        return pageFilmShowDtoMapper.toPageDto(pageFilmShow);
    }
}
