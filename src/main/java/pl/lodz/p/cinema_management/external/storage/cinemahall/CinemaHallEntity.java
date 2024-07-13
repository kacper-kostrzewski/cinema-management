package pl.lodz.p.cinema_management.external.storage.cinemahall;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CinemaHallEntity {

    @EqualsAndHashCode.Include
    @Id
    @SequenceGenerator(
            name = "cinemahall_id_seq",
            sequenceName = "cinemahall_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cinemahall_id_seq"
    )
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer capacity;
}
