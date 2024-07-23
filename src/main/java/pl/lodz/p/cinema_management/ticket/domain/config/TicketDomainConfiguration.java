package pl.lodz.p.cinema_management.ticket.domain.config;

import org.springframework.context.annotation.Bean;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowRepository;
import pl.lodz.p.cinema_management.filmshow.domain.seat.SeatRepository;
import pl.lodz.p.cinema_management.ticket.domain.TicketRepository;
import pl.lodz.p.cinema_management.ticket.domain.TicketService;
import pl.lodz.p.cinema_management.ticket.infrastructure.JpaTicketRepository;
import pl.lodz.p.cinema_management.ticket.infrastructure.TicketDatabaseStorageAdapter;
import pl.lodz.p.cinema_management.user.domain.UserRepository;

public class TicketDomainConfiguration {

    @Bean
    public TicketRepository ticketRepository(JpaTicketRepository jpaTicketRepository) {
        return new TicketDatabaseStorageAdapter(jpaTicketRepository);
    }

    @Bean
    public TicketService ticketService(TicketRepository ticketRepository,
                                       FilmShowRepository filmShowRepository,
                                       SeatRepository seatRepository,
                                       UserRepository userRepository
                                       ) {
        return new TicketService(ticketRepository, filmShowRepository, seatRepository, userRepository);
    }

}
