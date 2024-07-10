package pl.lodz.p.cinema_management.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.cinema_management.api.Film.FilmDtoMapper;
import pl.lodz.p.cinema_management.domain.Film.FilmRepository;
import pl.lodz.p.cinema_management.domain.Film.FilmService;
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
}
