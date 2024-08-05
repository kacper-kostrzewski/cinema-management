package pl.lodz.p.cinema_management.film.application;

import pl.lodz.p.cinema_management.film.infrastructure.storage.FilmRepository;
import pl.lodz.p.cinema_management.film.infrastructure.storage.FilmStorageAdapter;
import pl.lodz.p.cinema_management.film.infrastructure.storage.JpaFilmRepository;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("film.domain.properties")
public class FilmDomainConfiguration {

    @Bean
    public FilmRepository filmRepository(JpaFilmRepository jpaFilmRepository) {
        return new FilmStorageAdapter(jpaFilmRepository);
    }

}
