package pl.lodz.p.cinema_management.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.lodz.p.cinema_management.user.application.UserService;
import pl.lodz.p.cinema_management.user.domain.User;

@Component
@RequiredArgsConstructor
public class Security {

    private final UserService userService;

    public User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
    }
}
