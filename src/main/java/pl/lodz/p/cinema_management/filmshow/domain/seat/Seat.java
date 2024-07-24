package pl.lodz.p.cinema_management.filmshow.domain.seat;

import jakarta.persistence.*;
import lombok.*;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShow;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "film_shows_seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmShowId", nullable = false)
    private FilmShow filmShow;

    @Column(nullable = false, name = "row_number")
    private Integer rowNumber;

    @Column(nullable = false, name = "seat_number")
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "seat_status")
    private SeatStatus seatStatus;

}
