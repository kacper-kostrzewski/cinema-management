package pl.lodz.p.cinema_management.user.infrastructure.storage;

import pl.lodz.p.cinema_management.user.domain.PageUser;
import pl.lodz.p.cinema_management.user.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    void update(User user);

    void remove(Integer id);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    PageUser findAll(Pageable pageable);

}