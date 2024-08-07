package pl.lodz.p.cinema_management.filmshow.command.application;

import pl.lodz.p.cinema_management.filmshow.command.domain.FilmShowRepository;
import pl.lodz.p.cinema_management.filmshow.command.infrastructure.storage.JpaFilmShowRepository;
import pl.lodz.p.cinema_management.filmshow.command.infrastructure.storage.FilmShowStorageAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("filmshow.domain.properties")
public class FilmShowDomainConfiguration {

    @Bean
    public FilmShowRepository filmShowRepository(JpaFilmShowRepository jpaFilmShowRepository) {
        return new FilmShowStorageAdapter(jpaFilmShowRepository);
    }

}
