package ma.ensa.researchgate.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.ensa.researchgate.dto.AuthenticationResponse;
import ma.ensa.researchgate.dto.LoginRequest;
import ma.ensa.researchgate.dto.RefreshTokenRequest;
import ma.ensa.researchgate.service.serviceimpl.Userserviceimpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final Userserviceimpl authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Logged in!");
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequest));
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<AuthenticationResponse> refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        System.out.println("Token refreshed!");
        return ResponseEntity.status(HttpStatus.OK).body(authService.refreshToken(refreshTokenRequest));
    }

}
