package pl.lodz.p.cinema_management.filmshow.command.application;

import pl.lodz.p.cinema_management.filmshow.command.domain.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    Authentication getAuthentication();
    User getLoggedInUser();
}
