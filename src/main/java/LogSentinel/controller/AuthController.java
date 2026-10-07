package LogSentinel.controller;

import LogSentinel.dto.AuthResponse;
import LogSentinel.dto.LoginRequest;
import LogSentinel.dto.RegisterRequest;
import LogSentinel.dto.UserResponse;
import LogSentinel.entity.User;
import LogSentinel.service.JwtService;
import LogSentinel.service.RedisTokenService;
import LogSentinel.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    private final JwtService jwtService;

    private final RedisTokenService redisTokenService;

    public AuthController(
            UserService userService,
            JwtService jwtService,
            RedisTokenService redisTokenService) {

        this.userService = userService;
        this.jwtService = jwtService;
        this.redisTokenService = redisTokenService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(
            @Valid @RequestBody RegisterRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole("DEVELOPER");

        User savedUser =
                userService.registerUser(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request) {

        String token =
                userService.loginUser(request);

        return new AuthResponse(token);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public String logout(
            HttpServletRequest request) {

        String authHeader =
                request.getHeader("Authorization");

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            return "No active token found";
        }

        String token =
                authHeader.substring(7);

        long remainingSeconds =
                jwtService.getRemainingExpirationSeconds(
                        token
                );

        redisTokenService.revokeToken(
                token,
                remainingSeconds
        );

        return "Logout successful";
    }
}
