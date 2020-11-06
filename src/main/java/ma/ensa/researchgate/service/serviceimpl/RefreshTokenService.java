package ma.ensa.researchgate.service.serviceimpl;

import lombok.AllArgsConstructor;
import ma.ensa.researchgate.dao.RefreshTokenRepository;
import ma.ensa.researchgate.entities.RefreshToken;
import ma.ensa.researchgate.exceptions.ResearchgateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(String email) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        refreshToken.setEmail(email);
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validateRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResearchgateException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String email) {
        refreshTokenRepository.deleteByEmail(email);
    }
}
