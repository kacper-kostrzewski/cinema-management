package pl.lodz.p.cinema_management.user.infrastructure.storage;

import pl.lodz.p.cinema_management.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
