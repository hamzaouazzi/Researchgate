package ma.ensa.researchgate.service.serviceimpl;

import lombok.AllArgsConstructor;
import ma.ensa.researchgate.dao.RoleRepository;
import ma.ensa.researchgate.dao.UserRepository;
import ma.ensa.researchgate.dto.AuthenticationResponse;
import ma.ensa.researchgate.dto.LoginRequest;
import ma.ensa.researchgate.dto.RefreshTokenRequest;
import ma.ensa.researchgate.dto.RegisterRequest;
import ma.ensa.researchgate.entities.RefreshToken;
import ma.ensa.researchgate.entities.User;
import ma.ensa.researchgate.exceptions.UserAlreadyExistAuthenticationException;
import ma.ensa.researchgate.security.JwtProvider;
import ma.ensa.researchgate.service.Userservice;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;


@Service
@Transactional
@AllArgsConstructor
public class Userserviceimpl implements Userservice {

    private final UserRepository userdao;
    private final RoleRepository roledao;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @Override
    public User adduser(RegisterRequest registerRequest) throws UserAlreadyExistAuthenticationException {
        User user = new User();
        EmailValidator validator = EmailValidator.getInstance();
        if (validator.isValid(registerRequest.getEmail()) && userdao.findByEmail(registerRequest.getEmail()).isEmpty()){
            user.setEmail(registerRequest.getEmail());
            user.setFirstname(registerRequest.getFirstname());
            user.setLastname(registerRequest.getLastname());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setExpertise(registerRequest.getExpertise());
            user.setMobile(registerRequest.getMobile());
            user.setAddress(registerRequest.getAddress());
            user.setCreated_at(new Date());
            user.setEnabled(true);
            user.setRole(roledao.findByRole("AUTHOR"));
            userdao.save(user);
        }
        else{
            throw new UserAlreadyExistAuthenticationException(
                    "Email address invalid or already exists : "
                            +  registerRequest.getEmail());
        }
        return user;
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken(loginRequest.getEmail()).getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getEmail())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshTokenBean = refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenBean.getEmail());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenBean.getEmail())
                .build();
    }

    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userdao.findByEmail(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found - " + principal.getUsername()));
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

}
