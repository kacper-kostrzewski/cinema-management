package pl.lodz.p.cinema_management.ticket.domain.config;

import org.springframework.context.annotation.Bean;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowRepository;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowService;
import pl.lodz.p.cinema_management.filmshow.domain.seat.SeatRepository;
import pl.lodz.p.cinema_management.filmshow.domain.seat.SeatService;
import pl.lodz.p.cinema_management.ticket.domain.TicketRepository;
import pl.lodz.p.cinema_management.ticket.domain.TicketService;
import pl.lodz.p.cinema_management.ticket.infrastructure.JpaTicketRepository;
import pl.lodz.p.cinema_management.ticket.infrastructure.TicketDatabaseStorageAdapter;
import pl.lodz.p.cinema_management.user.domain.UserRepository;
import pl.lodz.p.cinema_management.user.domain.UserService;

public class TicketDomainConfiguration {

    @Bean
    public TicketRepository ticketRepository(JpaTicketRepository jpaTicketRepository) {
        return new TicketDatabaseStorageAdapter(jpaTicketRepository);
    }

    @Bean
    public TicketService ticketService(TicketRepository ticketRepository,
                                       FilmShowService filmShowService,
                                       SeatService seatService,
                                       UserService userService
                                       ) {
        return new TicketService(ticketRepository, filmShowService, seatService, userService);
    }

}
