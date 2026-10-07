package LogSentinel.config;

import LogSentinel.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final int MAX_REQUESTS = 5;
    private static final long WINDOW_SECONDS = 60;

    private final RedisService redisService;

    public RateLimitInterceptor(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        String clientIp = request.getRemoteAddr();

        String key = "rate-limit:" + clientIp;

        Object currentValue = redisService.get(key);

        int requestCount = 0;

        if (currentValue != null) {
            requestCount = Integer.parseInt(currentValue.toString());
        }

        if (requestCount >= MAX_REQUESTS) {
            response.setStatus(429);
            response.setContentType("application/json");
            return false;
        }

        redisService.set(
                key,
                requestCount + 1,
                WINDOW_SECONDS
        );

        return true;
    }
}