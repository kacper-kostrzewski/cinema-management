package pl.lodz.p.cinema_management.reservation.command.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "reservation_number_unique",
                        columnNames = "reservationNumber"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {
    @Id
    @SequenceGenerator(
            name = "reservation_id_seq",
            sequenceName = "reservation_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservation_id_seq"
    )
    Integer id;

    @Column
    String reservationNumber;

    @Column(nullable = false)
    String cinemaHallName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Seat> seats = new ArrayList<>();

    @Version
    private Integer version;

    @Transient
    BookingPolicy bookingPolicy;

    @Transient
    ReleasingPolicy releasingPolicy;

    Reservation(final String reservationNumber, final String cinemaHallName, List<String> seatsIdentifiers) {
        this.reservationNumber = reservationNumber;
        this.cinemaHallName = cinemaHallName;
        for (String identifier : seatsIdentifiers) {
            this.seats.add(new Seat(identifier));
        }
    }


    public void bookSeats(Integer userId, List<String> seatsIdentifiers) {
        if (bookingPolicy == null) {
            throw new IllegalStateException("Booking policy not set");
        }

        bookingPolicy.bookSeats(this, userId, seatsIdentifiers);
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

}
