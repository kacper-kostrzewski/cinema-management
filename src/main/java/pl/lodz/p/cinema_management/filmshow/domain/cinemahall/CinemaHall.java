package pl.lodz.p.cinema_management.filmshow.domain.cinemahall;

import lombok.*;
import pl.lodz.p.cinema_management.annotation.architecture.PrimaryPort;
import pl.lodz.p.cinema_management.annotation.ddd.AggregateRoot;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AggregateRoot
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "cinema_halls")
public class CinemaHall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinemaHallId")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

    @ElementCollection
    @CollectionTable(name = "seats", joinColumns = @JoinColumn(name = "cinemaHallId"))
    private List<Seat> seats;

    @PrimaryPort
    public void generateSeats() {
        if (seats == null || seats.isEmpty()) {
            this.seats = generateSeats(capacity);
        }
    }

    @PrimaryPort
    private List<Seat> generateSeats(int capacity) {
        int rows = (int) Math.ceil(Math.sqrt(capacity));
        int columns = (int) Math.ceil((double) capacity / rows);

        return IntStream.range(0, rows)
                .boxed()
                .flatMap(row -> IntStream.range(0, columns)
                        .mapToObj(col -> new Seat(row + 1, col + 1, SeatStatus.AVAILABLE)))
                .limit(capacity)
                .collect(Collectors.toList());
    }

}
