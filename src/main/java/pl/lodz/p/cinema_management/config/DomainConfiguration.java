package pl.lodz.p.cinema_management.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.cinema_management.api.cinemahall.CinemaHallDtoMapper;
import pl.lodz.p.cinema_management.api.film.FilmDtoMapper;
import pl.lodz.p.cinema_management.domain.cinemahall.CinemaHallRepository;
import pl.lodz.p.cinema_management.domain.cinemahall.CinemaHallService;
import pl.lodz.p.cinema_management.domain.film.FilmRepository;
import pl.lodz.p.cinema_management.domain.film.FilmService;
import pl.lodz.p.cinema_management.external.storage.cinemahall.CinemaHallMemoryStorageAdapter;
import pl.lodz.p.cinema_management.external.storage.film.FilmMemoryStorageAdapter;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {
    @Bean
    public FilmRepository filmRepository(FilmDtoMapper filmDtoMapper) {
        return new FilmMemoryStorageAdapter(filmDtoMapper);
    }

    @Bean
    public FilmService filmService(FilmRepository filmRepository) {
        return new FilmService(filmRepository);
    }

    @Bean
    public CinemaHallRepository cinemaHallRepository(CinemaHallDtoMapper cinemaHallDtoMapper) {
        return new CinemaHallMemoryStorageAdapter(cinemaHallDtoMapper);
    }

    @Bean
    public CinemaHallService cinemaHallService(CinemaHallRepository cinemaHallRepository) {
        return new CinemaHallService(cinemaHallRepository);
    }
}
