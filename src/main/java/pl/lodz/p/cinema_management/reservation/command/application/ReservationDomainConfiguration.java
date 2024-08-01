package pl.lodz.p.cinema_management.reservation.command.application;

import pl.lodz.p.cinema_management.reservation.command.domain.ReservationRepository;
import pl.lodz.p.cinema_management.reservation.command.infrastructure.storage.JpaReservationRepository;
import pl.lodz.p.cinema_management.reservation.command.infrastructure.storage.ReservationStorageAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("reservation.domain.properties")
public class ReservationDomainConfiguration {

    @Bean
    public ReservationRepository reservationRepository(JpaReservationRepository jpaUserRepository) {
        return new ReservationStorageAdapter(jpaUserRepository);
    }

}
