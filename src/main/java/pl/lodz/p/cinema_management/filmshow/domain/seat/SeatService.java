package pl.lodz.p.cinema_management.filmshow.domain.seat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public Seat findAndReserveSeat(Integer filmShowId, Integer rowNumber, Integer seatNumber) {
        // Find the seat by row number, seat number, and film show ID
        Seat seat = seatRepository.findAll().stream()
                .filter(s -> s.getRowNumber().equals(rowNumber) &&
                        s.getSeatNumber().equals(seatNumber) &&
                        s.getFilmShow().getId().equals(filmShowId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Seat does not belong to the specified film show"));

        // Check if the seat is already occupied
        if (seat.getSeatStatus() == SeatStatus.OCCUPIED) {
            throw new IllegalStateException("Seat is already occupied");
        }

        // If the seat is not occupied update the seat status to occupied
        seat.setSeatStatus(SeatStatus.OCCUPIED);
        seatRepository.save(seat);

        return seat;
    }

    public void releaseSeat(Seat seat) {
        seat.setSeatStatus(SeatStatus.AVAILABLE);
        seatRepository.save(seat);
    }

}
