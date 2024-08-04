package pl.lodz.p.cinema_management.availability.command.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "cinema_halls_availability",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "cinema_hall_cinemaHallName_unique",
                        columnNames = "cinemaHallName"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CinemaHallAvailability {

    @Id
    @SequenceGenerator(
            name = "cinema_hall_availability_id_seq",
            sequenceName = "cinema_hall_availability_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cinema_hall_availability_id_seq"
    )
    Integer id;

    @Column(nullable = false)
    String cinemaHallName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<ReservationLock> reservationLocks = new ArrayList<>();

    @Version
    Integer version;


    public CinemaHallAvailability(String cinemaHallName) {
        this.cinemaHallName = cinemaHallName;
    }

    public void lockTimeFrame(ReservationLock newLock) {
        for (ReservationLock lock : reservationLocks) {
            if (lock.getTimeFrame().overlaps(newLock.getTimeFrame())) {
                throw new OverlappingTimeFrameException();
            }
        }
        reservationLocks.add(newLock);
    }

    public void unlockTimeFrame(String reservationNumber) {
        reservationLocks.removeIf(lock -> lock.getReservationNumber().equals(reservationNumber));
    }


}
