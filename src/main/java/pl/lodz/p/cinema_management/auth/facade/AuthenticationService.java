package pl.lodz.p.cinema_management.auth.facade;

import pl.lodz.p.cinema_management.auth.api.AuthUserDto;
import pl.lodz.p.cinema_management.auth.api.AuthUserDtoMapper;
import pl.lodz.p.cinema_management.auth.api.AuthenticationRequest;
import pl.lodz.p.cinema_management.auth.api.AuthenticationResponse;
import pl.lodz.p.cinema_management.security.JWTUtil;
import pl.lodz.p.cinema_management.security.Security;
import pl.lodz.p.cinema_management.security.UserDetailsImpl;
import pl.lodz.p.cinema_management.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final AuthUserDtoMapper userDtoMapper;
    private final JWTUtil jwtUtil;
    private final Security security;

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        AuthUserDto userDto = userDtoMapper.toDto(principal.getUser());
        String token = jwtUtil.issueToken(userDto.email(), userDto.role());
        return new AuthenticationResponse(token, userDto);
    }

    public AuthUserDto getLoggedInUser() {
        User user = security.getPrincipal();

        return userDtoMapper.toDto(user);
    }

}
