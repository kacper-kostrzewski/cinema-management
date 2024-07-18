package pl.lodz.p.cinema_management.filmshow.domain.cinemahall;

import pl.lodz.p.cinema_management.annotation.ddd.AggregateRoot;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AggregateRoot
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CinemaHall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinemaHallId")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

}
