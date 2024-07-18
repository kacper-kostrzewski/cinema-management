package pl.lodz.p.cinema_management.user.domain.config;

import org.springframework.context.annotation.Bean;
import pl.lodz.p.cinema_management.user.domain.UserRepository;
import pl.lodz.p.cinema_management.user.domain.UserService;
import pl.lodz.p.cinema_management.user.infrastructure.JpaUserRepository;
import pl.lodz.p.cinema_management.user.infrastructure.UserDatabaseStorageAdapter;

public class UserDomainConfiguration {

    @Bean
    public UserRepository userRepository(JpaUserRepository jpaUserRepository) {
        return new UserDatabaseStorageAdapter(jpaUserRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }

}
