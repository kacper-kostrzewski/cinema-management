package pl.lodz.p.cinema_management.ticket.command.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "tickets",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "ticket_number_unique",
                        columnNames = "ticketNumber"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @SequenceGenerator(
            name = "ticket_id_seq",
            sequenceName = "ticket_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ticket_id_seq"
    )
    Integer id;

    @Embedded
    @Column(nullable = false)
    TicketNumber ticketNumber;

    @Embedded
    @Column(nullable = false)
    FilmShowId filmShowId;

    @Embedded
    @Column(nullable = false)
    UserId userId;

    @Embedded
    @Column(nullable = false)
    SeatId seatId;

    public Ticket(TicketNumber ticketNumber, FilmShowId filmShowId, UserId userId, SeatId seatId) {
        this.ticketNumber = ticketNumber;
        this.filmShowId = filmShowId;
        this.userId = userId;
        this.seatId = seatId;
    }

}

