package pl.lodz.p.cinema_management.payment.command.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "payments",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "payment_number_unique",
                        columnNames = "paymentNumber"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {

    @Id
    @SequenceGenerator(
            name = "payment_id_seq",
            sequenceName = "payment_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payment_id_seq"
    )
    Integer id;

    @Embedded
    @Column(nullable = false)
    PaymentNumber paymentNumber;

    @Embedded
    @Column(nullable = false)
    OrderId orderId;

    @Embedded
    @Column(nullable = false)
    UserId userId;

    @Embedded
    @Column(nullable = false)
    FilmShowId filmShowId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "payments_seats_identifiers", joinColumns = @JoinColumn(name = "payments_id"))
    @Column(name = "seat_id", nullable = false)
    List<SeatId> seats = new ArrayList<>();

    @Embedded
    @Column(nullable = false)
    Price price;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime createdAt;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime completedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    @Version
    Integer version;

    public Payment(PaymentNumber paymentNumber, OrderId orderId, UserId userId, FilmShowId filmShowId, List<SeatId> seats, Price price) {
        this.paymentNumber = paymentNumber;
        this.orderId = orderId;
        this.userId = userId;
        this.filmShowId = filmShowId;
        this.seats = seats;
        this.price = price;
        this.status = PaymentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void pay() {
        this.status = PaymentStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

}
