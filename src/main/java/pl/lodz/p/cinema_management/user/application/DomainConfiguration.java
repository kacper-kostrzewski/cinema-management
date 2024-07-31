package pl.lodz.p.cinema_management.user.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.cinema_management.user.domain.UserRepository;
import pl.lodz.p.cinema_management.user.infrastructure.storage.JpaUserRepository;
import pl.lodz.p.cinema_management.user.infrastructure.storage.UserEntityMapper;
import pl.lodz.p.cinema_management.user.infrastructure.storage.UserStorageAdapter;

@Configuration
@ConfigurationProperties("user.domain.properties")
public class DomainConfiguration {

    @Bean
    public UserRepository userRepository(JpaUserRepository jpaUserRepository, UserEntityMapper mapper) {
        return new UserStorageAdapter(jpaUserRepository, mapper);
    }

}
