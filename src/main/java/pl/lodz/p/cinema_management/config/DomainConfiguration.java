package pl.lodz.p.cinema_management.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.cinema_management.domain.cinemahall.CinemaHallRepository;
import pl.lodz.p.cinema_management.domain.cinemahall.CinemaHallService;
import pl.lodz.p.cinema_management.domain.film.FilmRepository;
import pl.lodz.p.cinema_management.domain.film.FilmService;
import pl.lodz.p.cinema_management.external.storage.cinemahall.CinemaHallDatabaseStorageAdapter;
import pl.lodz.p.cinema_management.external.storage.cinemahall.CinemaHallEntityMapper;
import pl.lodz.p.cinema_management.external.storage.cinemahall.JpaCinemaHallRepository;
import pl.lodz.p.cinema_management.external.storage.film.FilmDatabaseStorageAdapter;
import pl.lodz.p.cinema_management.external.storage.film.FilmEntityMapper;
import pl.lodz.p.cinema_management.external.storage.film.JpaFilmRepository;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {
    @Bean
    public FilmRepository filmRepository(JpaFilmRepository jpaFilmRepository, FilmEntityMapper filmEntityMapper) {
        return new FilmDatabaseStorageAdapter(jpaFilmRepository, filmEntityMapper);
    }

    @Bean
    public FilmService filmService(FilmRepository filmRepository) {
        return new FilmService(filmRepository);
    }

    @Bean
    public CinemaHallRepository cinemaHallRepository(JpaCinemaHallRepository jpaCinemaHallRepository, CinemaHallEntityMapper cinemaHallEntityMapper) {
        return new CinemaHallDatabaseStorageAdapter(jpaCinemaHallRepository, cinemaHallEntityMapper);
    }

    @Bean
    public CinemaHallService cinemaHallService(CinemaHallRepository cinemaHallRepository) {
        return new CinemaHallService(cinemaHallRepository);
    }
}
