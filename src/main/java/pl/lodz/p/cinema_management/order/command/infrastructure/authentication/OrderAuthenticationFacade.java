package pl.lodz.p.cinema_management.order.command.infrastructure.authentication;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.security.UserDetailsImpl;
import pl.lodz.p.cinema_management.order.command.application.AuthenticationService;
import pl.lodz.p.cinema_management.user.domain.User;
import pl.lodz.p.cinema_management.user.domain.UserService;


@Component
@RequiredArgsConstructor
public class OrderAuthenticationFacade implements AuthenticationService {
    private final UserService userService;
    private final OrderAuthenticationMapper mapper;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public pl.lodz.p.cinema_management.order.command.domain.User getLoggedInUser() {
        Authentication authentication = getAuthentication();
        User user = userService.findByEmail(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
        return mapper.toOrderContext(user);
    }

}