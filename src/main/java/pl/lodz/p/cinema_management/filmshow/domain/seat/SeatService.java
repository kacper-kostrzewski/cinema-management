package pl.lodz.p.cinema_management.filmshow.domain.seat;

import org.springframework.stereotype.Service;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShow;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowNotFoundException;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowRepository;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final FilmShowRepository filmShowRepository;


    public SeatService(SeatRepository seatRepository, FilmShowRepository filmShowRepository) {
        this.seatRepository = seatRepository;
        this.filmShowRepository = filmShowRepository;
    }

    public void changeSeatStatus(Integer filmShowId, Integer rowNumber, Integer seatNumber, SeatStatus newStatus) {
        // Find the FilmShow
        FilmShow filmShow = filmShowRepository.findById(filmShowId)
                .orElseThrow(FilmShowNotFoundException::new);

        // Find the Seat in the FilmShow
        Seat seat = filmShow.getSeats().stream()
                .filter(s -> s.getRowNumber().equals(rowNumber) && s.getSeatNumber().equals(seatNumber))
                .findFirst()
                .orElseThrow(SeatNotFoundException::new);

        // Update the seat status
        seat.setSeatStatus(newStatus);

        // Save the updated seat (or update FilmShow if necessary)
        seatRepository.save(seat);
    }

}
