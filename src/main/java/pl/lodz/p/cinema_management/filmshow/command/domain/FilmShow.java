package pl.lodz.p.cinema_management.filmshow.command.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "film_shows",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "film_show_number_unique",
                        columnNames = "filmShowNumber"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FilmShow {
    @Id
    @SequenceGenerator(
            name = "film_show_id_seq",
            sequenceName = "film_show_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "film_show_id_seq"
    )
    Integer id;

    @Column(nullable = false)
    String filmShowNumber;

    @Column(nullable = false)
    String cinemaHallName;

    @Column(nullable = false)
    String filmName;

    @Column(nullable = false)
    LocalDateTime filmShowDateTime;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Seat> seats = new ArrayList<>();

    @Version
    private Integer version;

    @Transient
    BookingPolicy bookingPolicy;

    @Transient
    ReleasingPolicy releasingPolicy;

    FilmShow(final String filmShowNumber, final String cinemaHallName, final List<String> seatsIdentifiers, final String filmName, final LocalDateTime filmShowDateTime) {
        this.filmShowNumber = filmShowNumber;
        this.cinemaHallName = cinemaHallName;
        for (String identifier : seatsIdentifiers) {
            this.seats.add(new Seat(identifier));
        }
        this.filmName = filmName;
        this.filmShowDateTime = filmShowDateTime;
    }

    public void bookSeats(Integer userId, List<String> seatsIdentifiers) {
        if (bookingPolicy == null) {
            throw new IllegalStateException("Booking policy not set");
        }

        bookingPolicy.bookSeats(this, userId, seatsIdentifiers);
    }


    public void confirmSeats(Integer userId, List<String> seatsIdentifiers, Integer duration) {
        validateSeatsForUser(userId, seatsIdentifiers);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime filmShowEndTime = this.filmShowDateTime.plusMinutes(duration + 30);

        if (filmShowEndTime.isBefore(now)) {
            throw new FilmShowEndedException();
        }

        for (String seatIdentifier : seatsIdentifiers) {
            Seat seat = findSeat(seatIdentifier);
            seat.confirmSeat(filmShowEndTime);
        }
    }

    public void releaseSeats(Integer userId, List<String> seatsIdentifiers) {
        if (releasingPolicy == null) {
            throw new IllegalStateException("Releasing policy not set");
        }
        releasingPolicy.releaseSeats(this, userId, seatsIdentifiers);
    }

    Seat findSeat(String seatsIdentifier) {
        for (Seat seat : seats) {
            if (seat.getSeatIdentifier().equals(seatsIdentifier)) {
                return seat;
            }
        }

        return null;
    }

    public void validateSeatsForUser(Integer userId, List<String> seatsIdentifiers) {
        for (String seatIdentifier : seatsIdentifiers) {
            Seat seat = findSeat(seatIdentifier);
            if (seat == null) {
                throw new SeatNotFoundException();
            }
            if (!seat.isTakenBy(userId)) {
                throw new SeatAlreadyTakenException();
            }
            if (seat.getTemporarilyBookedTo().isBefore(LocalDateTime.now())) {
                throw new ReservationTimedOutException();
            }
        }
    }

    public List<String> getReservedSeatsForUser(Integer userId) {
        List<String> reservedSeats = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.isTakenBy(userId)) {
                reservedSeats.add(seat.getSeatIdentifier());
            }
        }
        return reservedSeats;
    }

}
