package pl.lodz.p.cinema_management.cinemahall.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "cinema_halls",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "cinema_hall_name_unique",
                        columnNames = "name"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CinemaHall {

    @Id
    @SequenceGenerator(
            name = "cinema_hall_id_seq",
            sequenceName = "cinema_hall_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cinema_hall_id_seq"
    )
    private Integer id;
    @Column(
            nullable = false
    )
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Seat> seats = new ArrayList<>();

    public CinemaHall(String name, List<Seat> seats) {
        this.name = name;
        this.seats = seats;
    }

}
