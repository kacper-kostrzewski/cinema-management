package pl.lodz.p.cinema_management.film.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "films",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "film_name_unique",
                        columnNames = "name"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Film {

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
    private String name;

    @Column(nullable = false)
    private Integer duration;

    public Film(String name, Integer duration) {
        this.name = name;
        this.duration = duration;
    }

}
