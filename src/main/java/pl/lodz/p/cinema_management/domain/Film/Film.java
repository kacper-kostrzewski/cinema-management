package pl.lodz.p.cinema_management.domain.Film;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class Film {
    private Integer id;
    private String title;
    private String genre;
    private String director;
    private String cast;
    private Integer duration;
    private LocalDate releaseDate;
    private String production;
    private String synopsis;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(id, film.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
