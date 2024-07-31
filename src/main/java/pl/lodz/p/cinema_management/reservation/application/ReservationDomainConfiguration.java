package pl.lodz.p.cinema_management.reservation.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.cinema_management.reservation.domain.ReservationRepository;
import pl.lodz.p.cinema_management.reservation.infrastructure.storage.JpaReservationRepository;
import pl.lodz.p.cinema_management.reservation.infrastructure.storage.ReservationStorageAdapter;

@Configuration
@ConfigurationProperties("reservation.domain.properties")
public class ReservationDomainConfiguration {

    @Bean
    public ReservationRepository reservationRepository(JpaReservationRepository jpaUserRepository) {
        return new ReservationStorageAdapter(jpaUserRepository);
    }

}
