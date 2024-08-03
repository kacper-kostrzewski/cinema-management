package pl.lodz.p.cinema_management.reservation.command.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "ReservationSeat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue
    Integer id;
    @Column(
            nullable = false
    )
    String seatIdentifier;
    @Column(
            nullable = true
    )
    Integer takenBy;

    Seat(final String seatIdentifier) {
        this.seatIdentifier = seatIdentifier;
    }

    Seat(final String seatIdentifier, final Integer takenBy) {
        this.seatIdentifier = seatIdentifier;
        this.takenBy = takenBy;
    }

    public boolean isTaken() {
        return takenBy != null;
    }

    public void takeBy(Integer userId) {
        if (takenBy != null) {
            throw new SeatAlreadyTakenException();
        }
        takenBy = userId;
    }

    public void release() {
        takenBy = null;
    }

    boolean isTakenBy(Integer userId) {
        return userId.equals(takenBy);
    }
}
