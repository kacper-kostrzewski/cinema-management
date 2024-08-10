package pl.lodz.p.cinema_management.filmshow.command.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "FilmShowSeat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    String seatIdentifier;

    @Column
    Integer takenBy;

    @Column
    LocalDateTime temporarilyBookedTo;

    Seat(final String seatIdentifier) {
        this.seatIdentifier = seatIdentifier;
    }

    public boolean isTaken() {
        return takenBy != null;
    }

    public void takeBy(Integer userId) {
        if (this.takenBy != null && this.temporarilyBookedTo.isAfter(LocalDateTime.now())) {
            throw new SeatAlreadyTakenException();
        }
        this.takenBy = userId;
        this.temporarilyBookedTo = LocalDateTime.now().plusMinutes(1);
    }

    public void confirmSeat(LocalDateTime filmShowEnd) {
        this.temporarilyBookedTo = filmShowEnd;
    }

    public void release() {
        this.takenBy = null;
        this.temporarilyBookedTo = null;
    }

    boolean isTakenBy(Integer userId) {
        return userId.equals(takenBy);
    }
}
