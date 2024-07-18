package pl.lodz.p.cinema_management.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cinema_management.user.domain.User;

public interface JpaUserRepository extends JpaRepository<User, Integer> {
}
