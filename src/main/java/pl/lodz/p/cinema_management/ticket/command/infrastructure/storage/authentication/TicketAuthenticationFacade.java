package pl.lodz.p.cinema_management.ticket.command.infrastructure.storage.authentication;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.security.UserDetailsImpl;
import pl.lodz.p.cinema_management.ticket.command.application.AuthenticationService;
import pl.lodz.p.cinema_management.user.domain.User;
import pl.lodz.p.cinema_management.user.domain.UserService;


@Component
@RequiredArgsConstructor
public class TicketAuthenticationFacade implements AuthenticationService {
    private final UserService userService;
    private final TicketAuthenticationMapper mapper;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public pl.lodz.p.cinema_management.ticket.command.domain.User getLoggedInUser() {
        Authentication authentication = getAuthentication();
        User user = userService.findByEmail(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
        return mapper.toTicketContext(user);
    }

}