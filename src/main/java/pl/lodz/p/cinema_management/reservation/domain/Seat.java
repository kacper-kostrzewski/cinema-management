package pl.lodz.p.cinema_management.reservation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
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
    Integer seatNumber;
    @Column(
            nullable = true
    )
    Integer takenBy;

    Seat(final Integer seatNumber, final Integer takenBy) {
        this.seatNumber = seatNumber;
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
