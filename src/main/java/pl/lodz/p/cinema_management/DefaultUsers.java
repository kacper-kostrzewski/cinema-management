package pl.lodz.p.cinema_management;

import org.springframework.core.annotation.Order;
import pl.lodz.p.cinema_management.user.domain.User;
import pl.lodz.p.cinema_management.user.domain.UserRole;
import pl.lodz.p.cinema_management.user.domain.UserService;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Log
@Order(1)
public class DefaultUsers implements CommandLineRunner {

    private final UserService userService;

    public DefaultUsers(UserService userService) {
        this.userService = userService;
    }

    private final User adminUser = new User(
            null,
            "admin@gmail.com",
            "Admin",
            "password",
            UserRole.ADMIN
    );

    private final User vipUser = new User(
            null,
            "vip@gmail.com",
            "Vip",
            "password",
            UserRole.VIP
    );

    private final User medicalDoctorUser = new User(
            null,
            "user@gmail.com",
            "User",
            "password",
            UserRole.USER
    );

    @Override
    public void run(String... args) {
        try {
            addUser(adminUser);
            addUser(vipUser);
            addUser(medicalDoctorUser);
        } catch (Exception ex) {
            log.warning(ex.getMessage());
        }
    }

    private void addUser(User user) {
        userService.save(user);
    }
}
