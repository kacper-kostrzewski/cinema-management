package pl.lodz.p.cinema_management.filmshow.domain;

import jakarta.persistence.*;
import lombok.*;

import pl.lodz.p.cinema_management.annotation.ddd.AggregateRoot;
import pl.lodz.p.cinema_management.filmshow.domain.seat.Seat;
import pl.lodz.p.cinema_management.filmshow.domain.film.Film;
import pl.lodz.p.cinema_management.filmshow.domain.cinemahall.CinemaHall;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AggregateRoot
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "film_shows")
public class FilmShow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmId")
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinemaHallId")
    private CinemaHall cinemaHall;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @OneToMany(mappedBy = "filmShow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();
}
