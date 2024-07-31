package pl.lodz.p.cinema_management.reservation.infrastructure.authentication;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.reservation.application.AuthenticationService;
import pl.lodz.p.cinema_management.security.UserDetailsImpl;
import pl.lodz.p.cinema_management.user.application.UserService;
import pl.lodz.p.cinema_management.user.domain.User;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade implements AuthenticationService {
    private final UserService userService;
    private final UserAuthenticationMapper mapper;
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public pl.lodz.p.cinema_management.reservation.domain.User getLoggedInUser() {
        Authentication authentication = getAuthentication();
        User user = userService.findByEmail(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
        return mapper.toReservationContext(user);
    }

}