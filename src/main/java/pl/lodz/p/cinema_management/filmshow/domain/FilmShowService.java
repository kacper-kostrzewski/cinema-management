package pl.lodz.p.cinema_management.filmshow.domain;

import org.springframework.stereotype.Service;
import pl.lodz.p.cinema_management.filmshow.domain.cinemahall.*;
import pl.lodz.p.cinema_management.filmshow.domain.seat.Seat;
import pl.lodz.p.cinema_management.filmshow.domain.seat.SeatStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FilmShowService {

    private final FilmShowRepository filmShowRepository;
    private final CinemaHallRepository cinemaHallRepository;

    public FilmShowService(FilmShowRepository filmShowRepository, CinemaHallRepository cinemaHallRepository) {
        this.filmShowRepository = filmShowRepository;
        this.cinemaHallRepository = cinemaHallRepository;
    }

    public FilmShow addFilmShow(FilmShow filmShow) {
        // Fetch the CinemaHall to get its capacity
        CinemaHall cinemaHall = cinemaHallRepository.findById(filmShow.getCinemaHall().getId())
                .orElseThrow(CinemaHallNotFoundException::new);

        // Generate and set seats for the FilmShow
        List<Seat> seats = generateSeats(cinemaHall.getCapacity(), filmShow);
        filmShow.setSeats(seats);

        return filmShowRepository.save(filmShow);
    }

    public Optional<FilmShow> getFilmShowById(Integer id) {
        return filmShowRepository.findById(id);
    }

    public List<FilmShow> getAllFilmShows() {
        return filmShowRepository.findAll();
    }

    public FilmShow updateFilmShow(FilmShow filmShow) {
        // Fetch the CinemaHall to get its capacity
        Optional<CinemaHall> optionalCinemaHall = cinemaHallRepository.findById(filmShow.getCinemaHall().getId());
        if (optionalCinemaHall.isPresent()) {
            CinemaHall cinemaHall = optionalCinemaHall.get();

            // Generate and set seats for the FilmShow
            List<Seat> seats = generateSeats(cinemaHall.getCapacity(), filmShow);
            filmShow.setSeats(seats);

            return filmShowRepository.save(filmShow);
        } else {
            throw new CinemaHallNotFoundException();
        }
    }

    public void deleteFilmShow(Integer id) {
        filmShowRepository.delete(id);
    }

    private List<Seat> generateSeats(int capacity, FilmShow filmShow) {
        int rows = (int) Math.ceil(Math.sqrt(capacity));
        int columns = (int) Math.ceil((double) capacity / rows);

        return IntStream.range(0, rows)
                .boxed()
                .flatMap(row -> IntStream.range(0, columns)
                        .mapToObj(col -> createSeat(row + 1, col + 1, filmShow)))
                .limit(capacity)
                .collect(Collectors.toList());
    }

    private Seat createSeat(int rowNumber, int seatNumber, FilmShow filmShow) {
        Seat seat = new Seat();
        seat.setRowNumber(rowNumber);
        seat.setSeatNumber(seatNumber);
        seat.setSeatStatus(SeatStatus.AVAILABLE);
        seat.setFilmShow(filmShow);
        return seat;
    }

}
