package pl.lodz.p.cinema_management.ticket.command.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.cinema_management.ticket.command.domain.TicketRepository;
import pl.lodz.p.cinema_management.ticket.command.infrastructure.storage.JpaTicketRepository;
import pl.lodz.p.cinema_management.ticket.command.infrastructure.storage.TicketStorageAdapter;


@Configuration
@ConfigurationProperties("ticket.domain.properties")
public class TicketDomainConfiguration {

    @Bean
    public TicketRepository ticketRepository(JpaTicketRepository jpaTicketRepository) {
        return new TicketStorageAdapter(jpaTicketRepository);
    }

}
