package pl.lodz.p.cinema_management.filmshow.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import pl.lodz.p.cinema_management.annotation.ddd.AggregateRoot;
import pl.lodz.p.cinema_management.filmshow.domain.film.Film;
import pl.lodz.p.cinema_management.filmshow.domain.cinemahall.CinemaHall;

import java.time.LocalDateTime;

@AggregateRoot
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}
