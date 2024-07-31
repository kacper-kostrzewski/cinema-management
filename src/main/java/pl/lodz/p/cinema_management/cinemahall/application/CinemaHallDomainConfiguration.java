package pl.lodz.p.cinema_management.cinemahall.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.cinema_management.cinemahall.domain.CinemaHallRepository;
import pl.lodz.p.cinema_management.cinemahall.infrastructure.storage.CinemaHallDatabaseStorageAdapter;
import pl.lodz.p.cinema_management.cinemahall.infrastructure.storage.JpaCinemaHallRepository;

@Configuration
@ConfigurationProperties("cinemahall.domain.properties")
public class CinemaHallDomainConfiguration {

    @Bean
    public CinemaHallRepository cinemaHallRepository(JpaCinemaHallRepository jpaCinemaHallRepository) {
        return new CinemaHallDatabaseStorageAdapter(jpaCinemaHallRepository);
    }

}
