package pl.lodz.p.cinema_management.availability.command.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "ReservationTimeSlot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationTimeSlot {

    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    String filmShowNumber;

    @Embedded
    @Column(nullable = false)
    TimeSlot timeSlot;

    public ReservationTimeSlot(String filmShowNumber, TimeSlot timeSlot) {
        this.filmShowNumber = filmShowNumber;
        this.timeSlot = timeSlot;
    }
}
