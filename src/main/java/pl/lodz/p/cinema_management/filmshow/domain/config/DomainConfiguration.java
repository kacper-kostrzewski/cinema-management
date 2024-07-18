package pl.lodz.p.cinema_management.filmshow.domain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowRepository;
import pl.lodz.p.cinema_management.filmshow.domain.FilmShowService;
import pl.lodz.p.cinema_management.filmshow.domain.cinemahall.CinemaHallRepository;
import pl.lodz.p.cinema_management.filmshow.domain.cinemahall.CinemaHallService;
import pl.lodz.p.cinema_management.filmshow.domain.film.FilmRepository;
import pl.lodz.p.cinema_management.filmshow.domain.film.FilmService;
import pl.lodz.p.cinema_management.filmshow.infrastructure.FilmShowDatabaseStorageAdapter;
import pl.lodz.p.cinema_management.filmshow.infrastructure.JpaFilmShowRepository;
import pl.lodz.p.cinema_management.filmshow.infrastructure.cinemahall.CinemaHallDatabaseStorageAdapter;
import pl.lodz.p.cinema_management.filmshow.infrastructure.cinemahall.JpaCinemaHallRepository;
import pl.lodz.p.cinema_management.filmshow.infrastructure.film.FilmDatabaseStorageAdapter;
import pl.lodz.p.cinema_management.filmshow.infrastructure.film.JpaFilmRepository;


@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public FilmRepository filmRepository(JpaFilmRepository jpaFilmRepository) {
        return new FilmDatabaseStorageAdapter(jpaFilmRepository);
    }

    @Bean
    public FilmService filmService(FilmRepository filmRepository) {
        return new FilmService(filmRepository);
    }

    @Bean
    public CinemaHallRepository cinemaHallRepository(JpaCinemaHallRepository jpaCinemaHallRepository) {
        return new CinemaHallDatabaseStorageAdapter(jpaCinemaHallRepository);
    }

    @Bean
    public CinemaHallService cinemaHallService(CinemaHallRepository cinemaHallRepository) {
        return new CinemaHallService(cinemaHallRepository);
    }

    @Bean
    public FilmShowRepository filmShowRepository(JpaFilmShowRepository jpaFilmShowRepository) {
        return new FilmShowDatabaseStorageAdapter(jpaFilmShowRepository);
    }

    @Bean
    public FilmShowService filmShowService(FilmShowRepository filmShowRepository) {
        return new FilmShowService(filmShowRepository);
    }

}
