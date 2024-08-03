package pl.lodz.p.cinema_management.user.infrastructure.storage;


import pl.lodz.p.cinema_management.user.domain.PageUser;
import pl.lodz.p.cinema_management.user.domain.User;
import pl.lodz.p.cinema_management.user.domain.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
@Component
public
class UserStorageAdapter implements UserRepository {

    private final JpaUserRepository userRepository;

    @Override
    public User save(final User user) {
        try {
            User saved = userRepository.save(user);
            log.info("Saved entity " + saved);
            return saved;
        } catch (DataIntegrityViolationException ex) {
            log.warning("User " + user.getEmail() + " already exits in db");
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public void update(final User user) {
        userRepository.findById(user.getId()).ifPresent(userEntity -> userRepository.save(user));
    }

    @Override
    public void remove(final Integer id) {
        userRepository.findById(id).ifPresent(userEntity -> userRepository.deleteById(id));
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(final Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public PageUser findAll(final Pageable pageable) {
        Page<User> pageOfUsersEntity = userRepository.findAll(pageable);
        List<User> usersOnCurrentPage = pageOfUsersEntity.getContent().stream()
                .collect(Collectors.toList());
        return new PageUser(
                usersOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfUsersEntity.getTotalPages(),
                pageOfUsersEntity.getTotalElements()
        );
    }
}
