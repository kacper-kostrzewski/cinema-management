package pl.lodz.p.cinema_management.order.command.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "orders",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "order_number_unique",
                        columnNames = "orderNumber"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

    @Id
    @SequenceGenerator(
            name = "order_id_seq",
            sequenceName = "order_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_id_seq"
    )
    Integer id;

    @Embedded
    @Column(nullable = false)
    OrderNumber orderNumber;

    @Embedded
    @Column(nullable = false)
    UserId userId;

    @Embedded
    @Column(nullable = false)
    FilmShowId filmShowId;

    @Embedded
    @Column(nullable = false)
    Price price;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "orders_seats_identifiers", joinColumns = @JoinColumn(name = "orders_id"))
    @Column(name = "seat_id", nullable = false)
    List<SeatId> seats = new ArrayList<>();

    @Version
    Integer version;


    public Order(OrderNumber orderNumber, UserId userId, FilmShowId filmShowId, List<SeatId> seats) {
        this.orderNumber = orderNumber;
        this.userId = userId;
        this.filmShowId = filmShowId;
        this.seats = seats;
        this.price = Price.of(seats.size() * 25);
    }

}
