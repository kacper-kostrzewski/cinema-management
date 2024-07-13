package pl.lodz.p.cinema_management.domain.film;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;
import com.smalaca.annotation.ddd.AggregateRoot;
import lombok.EqualsAndHashCode;

@AggregateRoot
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Film {
    @EqualsAndHashCode.Include
    private Integer id;
    private String title;
    private String genre;
    private String director;
    private String stars;
    private Integer duration;
    private LocalDate releaseDate;
    private String production;
    private String synopsis;
}
