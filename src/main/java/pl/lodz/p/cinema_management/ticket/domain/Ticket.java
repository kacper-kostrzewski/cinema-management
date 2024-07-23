package pl.lodz.p.cinema_management.ticket.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.lodz.p.cinema_management.annotation.ddd.AggregateRoot;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShow;
import pl.lodz.p.cinema_management.filmshow.domain.seat.Seat;
import pl.lodz.p.cinema_management.user.domain.User;

import java.time.LocalDateTime;

@AggregateRoot
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filmShowId")
    private FilmShow filmShow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatId")
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false, name = "generation_time")
    private LocalDateTime generationTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "ticket_status")
    private TicketStatus ticketStatus;
}
