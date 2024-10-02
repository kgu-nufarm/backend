package api.kgu.nufarm.common.auth.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class TokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveAccessToken(String userId, String accessToken, long accessTokenDuration) {
        redisTemplate.opsForValue().set(userId + ":access", accessToken, Duration.ofMillis(accessTokenDuration));
    }

    public void saveRefreshToken(String userId, String refreshToken, long refreshTokenDuration) {
        redisTemplate.opsForValue().set(userId + ":refresh", refreshToken, Duration.ofMillis(refreshTokenDuration));
    }

    public String getAccessToken(String userId) {
        return redisTemplate.opsForValue().get(userId + ":access");
    }

    public String getRefreshToken(String userId) {
        return redisTemplate.opsForValue().get(userId + ":refresh");
    }

    public void deleteTokens(String userId) {
        redisTemplate.delete(userId + ":access");
        redisTemplate.delete(userId + ":refresh");
    }
}
