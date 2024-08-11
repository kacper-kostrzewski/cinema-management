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
    UserId userId;

    @Embedded
    @Column(nullable = false)
    FilmName filmName;

    @Embedded
    @Column(nullable = false)
    CinemaHallName cinemaHallName;

    @Embedded
    @Column(nullable = false)
    FilmShowDateTime filmShowDateTime;

    @Embedded
    @Column(nullable = false)
    SeatId seatId;

    @Embedded
    @Column(nullable = false)
    Price price;

    @Embedded
    @Column(nullable = false)
    GenerationTime generationTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TicketStatus ticketStatus;

    @Version
    Integer version;

    public Ticket(TicketNumber ticketNumber, UserId userId, FilmName filmName, CinemaHallName cinemaHallName, FilmShowDateTime filmShowDateTime, SeatId seatId, Price price, GenerationTime generationTime, TicketStatus ticketStatus) {
        this.ticketNumber = ticketNumber;
        this.userId = userId;
        this.filmName = filmName;
        this.cinemaHallName = cinemaHallName;
        this.filmShowDateTime = filmShowDateTime;
        this.seatId = seatId;
        this.price = price;
        this.generationTime = generationTime;
        this.ticketStatus = ticketStatus;
    }

    public void markAsUsed() {
        if (this.ticketStatus != TicketStatus.VALID) {
            throw new IllegalStateException("Ticket must be valid before it can be used.");
        }
        this.ticketStatus = TicketStatus.USED;
    }

    public void cancel() {
        if (this.ticketStatus == TicketStatus.USED) {
            throw new IllegalStateException("Cannot cancel a used ticket.");
        }
        this.ticketStatus = TicketStatus.INVALID;
    }

}

