package pl.lodz.p.cinema_management.order.command.application;

import org.springframework.security.core.Authentication;
import pl.lodz.p.cinema_management.order.command.domain.User;

public interface AuthenticationService {
    Authentication getAuthentication();
    User getLoggedInUser();
}
