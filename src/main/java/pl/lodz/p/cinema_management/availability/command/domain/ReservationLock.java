package pl.lodz.p.cinema_management.availability.command.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "ReservationLock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationLock {

    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    String reservationNumber;

    @Embedded
    @Column(nullable = false)
    TimeFrame timeFrame;

    public ReservationLock(String reservationNumber, TimeFrame timeFrame) {
        this.reservationNumber = reservationNumber;
        this.timeFrame = timeFrame;
    }
}
