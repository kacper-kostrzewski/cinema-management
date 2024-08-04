package pl.lodz.p.cinema_management.cinemahall.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "CinemaHallSeat")
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

}

