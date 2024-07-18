package pl.lodz.p.cinema_management.user.infrastructure;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import pl.lodz.p.cinema_management.user.domain.User;
import pl.lodz.p.cinema_management.user.domain.UserNotFoundException;
import pl.lodz.p.cinema_management.user.domain.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDatabaseStorageAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserDatabaseStorageAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public User update(User user) {
        Optional<User> existingUser = jpaUserRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            return jpaUserRepository.save(user);
        }
        throw new UserNotFoundException();
    }

    @Override
    public void delete(Integer id) {
        jpaUserRepository.deleteById(id);
    }

}
