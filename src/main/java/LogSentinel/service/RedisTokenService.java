package LogSentinel.service;

import org.springframework.stereotype.Service;

@Service
public class RedisTokenService {

    private static final String REVOKED_TOKEN_PREFIX =
            "revoked-token:";

    private final RedisService redisService;

    public RedisTokenService(RedisService redisService) {
        this.redisService = redisService;
    }

    public void revokeToken(String token, long expirationSeconds) {

        String key = REVOKED_TOKEN_PREFIX + token;

        redisService.set(
                key,
                true,
                expirationSeconds
        );
    }

    public boolean isTokenRevoked(String token) {

        String key = REVOKED_TOKEN_PREFIX + token;

        return redisService.get(key) != null;
    }
}