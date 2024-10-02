package api.kgu.nufarm.common.auth.service;

import api.kgu.nufarm.application.user.entity.Role;
import api.kgu.nufarm.application.user.entity.User;
import api.kgu.nufarm.application.user.service.UserService;
import api.kgu.nufarm.common.auth.dao.TokenRepository;
import api.kgu.nufarm.common.auth.dto.JwtTokenDto;
import api.kgu.nufarm.common.auth.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;

    // 로그인
    public JwtTokenDto login(String email, String password) {
        User user = userService.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            log.warn("Invalid email or password: {}", email);
            return null;
        }
        JwtTokenDto tokenDto = jwtTokenProvider.generateToken(user.getId(), user.getRole());

        tokenRepository.saveAccessToken(String.valueOf(user.getId()), tokenDto.getAccessToken(), jwtTokenProvider.getAccessTokenDuration());
        tokenRepository.saveRefreshToken(String.valueOf(user.getId()), tokenDto.getRefreshToken(), jwtTokenProvider.getRefreshTokenDuration());
        log.info("{}이 로그인 했습니다.", user.getUsername());
        return tokenDto;
    }

    // 로그아웃
    public void logout(Long userId) {
        tokenRepository.deleteTokens(String.valueOf(userId));
        log.info("{}이 로그아웃 했습니다.", userId);
    }

    // 토큰 재발급
    public JwtTokenDto reissueToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            log.warn("Invalid refresh token: {}", refreshToken);
            return null;
        }

        Claims claims = jwtTokenProvider.parseClaims(refreshToken);
        String userId = claims.getSubject();

        String redisRefreshToken = tokenRepository.getRefreshToken(userId);
        if (redisRefreshToken == null || !redisRefreshToken.equals(refreshToken)) {
            log.warn("Refresh token mismatch for user: {}", userId);
            return null;
        }

        Role userRole = jwtTokenProvider.getRoleFromToken(refreshToken);
        JwtTokenDto newTokenDto = jwtTokenProvider.generateToken(Long.parseLong(userId), userRole);

        tokenRepository.saveAccessToken(userId, newTokenDto.getAccessToken(), jwtTokenProvider.getAccessTokenDuration());

        return newTokenDto;
    }
}
