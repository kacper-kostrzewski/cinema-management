package pl.lodz.p.cinema_management.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.user.domain.UserService;

@RequiredArgsConstructor
@Component
public class DefaultUsers implements CommandLineRunner {
    private final UserDtoMapper userDtoMapper;
    private final UserService userService;

    private final UserDto users[] = {
            new UserDto(null, "user1@example.com", "password1"),
            new UserDto(null, "user2@example.com", "password2"),
            new UserDto(null, "user3@example.com", "password3")
    };

    @Override
    public void run(String... args) throws Exception {
        for (UserDto user : users) {
            userService.addUser(userDtoMapper.toDomain(user));
        }
    }
}
