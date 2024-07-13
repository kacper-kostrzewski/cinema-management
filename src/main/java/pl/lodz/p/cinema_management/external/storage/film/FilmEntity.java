package pl.lodz.p.cinema_management.external.storage.film;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FilmEntity {

    @EqualsAndHashCode.Include
    @Id
    @SequenceGenerator(
            name = "film_id_seq",
            sequenceName = "film_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "film_id_seq"
    )
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String stars;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private String production;

    @Column(nullable = false)
    private String synopsis;
}
