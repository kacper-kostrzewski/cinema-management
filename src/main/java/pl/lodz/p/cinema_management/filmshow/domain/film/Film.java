package pl.lodz.p.cinema_management.filmshow.domain.film;

import lombok.*;
import pl.lodz.p.cinema_management.annotation.ddd.AggregateRoot;

import jakarta.persistence.*;

import java.time.LocalDate;

@AggregateRoot
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "filmId")
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column (nullable = false)
    private String genre;

    @Column (nullable = false)
    private String director;

    @Column (nullable = false)
    private String stars;

    @Column (nullable = false)
    private Integer duration;

    @Column (nullable = false)
    private LocalDate releaseDate;

    @Column (nullable = false)
    private String production;

    @Column (nullable = false)
    private String synopsis;

}
