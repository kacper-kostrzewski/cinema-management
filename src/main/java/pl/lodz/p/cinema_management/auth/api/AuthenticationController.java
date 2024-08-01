package pl.lodz.p.cinema_management.auth.api;

import pl.lodz.p.cinema_management.auth.facade.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.token())
                .body(response);
    }

    @GetMapping("me")
    public ResponseEntity<AuthUserDto> aboutMe() {

        return ResponseEntity.ok(authenticationService.getLoggedInUser());
    }

}
