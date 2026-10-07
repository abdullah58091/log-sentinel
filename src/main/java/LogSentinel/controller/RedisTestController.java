package LogSentinel.controller;

import LogSentinel.service.RedisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cache/test")
public class RedisTestController {

    private final RedisService redisService;

    public RedisTestController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping
    public ResponseEntity<String> setValue(
            @RequestParam String key,
            @RequestParam String value) {

        redisService.set(key, value, 60);

        return ResponseEntity.ok("Value stored in Redis");
    }

    @GetMapping
    public ResponseEntity<Object> getValue(
            @RequestParam String key) {

        Object value = redisService.get(key);

        if (value == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(value);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteValue(
            @RequestParam String key) {

        redisService.delete(key);

        return ResponseEntity.ok("Value deleted from Redis");
    }
}