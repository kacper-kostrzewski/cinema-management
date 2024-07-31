package pl.lodz.p.cinema_management.reservation.application;

import org.springframework.security.core.Authentication;
import pl.lodz.p.cinema_management.reservation.domain.User;

public interface AuthenticationService {
    Authentication getAuthentication();
    User getLoggedInUser();
}
