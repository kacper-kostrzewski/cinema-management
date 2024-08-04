package pl.lodz.p.cinema_management.availability.command.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.cinema_management.availability.command.domain.CinemaHallAvailabilityRepository;
import pl.lodz.p.cinema_management.availability.command.infrastructure.storage.CinemaHallAvailabilityStorageAdapter;
import pl.lodz.p.cinema_management.availability.command.infrastructure.storage.JpaCinemaHallAvailabilityRepository;

@Configuration
@ConfigurationProperties("availability.domain.properties")
public class AvailabilityDomainConfiguration {

    @Bean
    public CinemaHallAvailabilityRepository cinemaHallAvailabilityRepository(JpaCinemaHallAvailabilityRepository jpaCinemaHallAvailabilityRepository) {
        return new CinemaHallAvailabilityStorageAdapter(jpaCinemaHallAvailabilityRepository);
    }


}
