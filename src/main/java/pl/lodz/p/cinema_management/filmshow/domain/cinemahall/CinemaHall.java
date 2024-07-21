package pl.lodz.p.cinema_management.filmshow.domain.cinemahall;

import lombok.*;
import pl.lodz.p.cinema_management.annotation.ddd.AggregateRoot;

import jakarta.persistence.*;

@AggregateRoot
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "cinema_halls")
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
