package LogSentinel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                // Disable CSRF because this application uses JWT
                .csrf(csrf -> csrf.disable())

                // Enable CORS configuration
                .cors(cors -> {})

                // Disable form-based login
                .formLogin(form -> form.disable())

                // Disable HTTP Basic authentication
                .httpBasic(basic -> basic.disable())

                // Use stateless JWT authentication
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // Public authentication endpoints
                        .requestMatchers("/api/auth/**").permitAll()

                        // AI chatbot endpoints
                        .requestMatchers("/api/ai/**").permitAll()

                        // Spring Boot Actuator endpoints
                        .requestMatchers("/actuator/**").permitAll()

                        // Log processing requires ADMIN or DEVELOPER
                        .requestMatchers("/api/log-processing")
                        .hasAnyRole("ADMIN", "DEVELOPER")

                        // Incident management requires ADMIN
                        .requestMatchers("/api/incidents/**")
                        .hasRole("ADMIN")

                        // Error endpoint
                        .requestMatchers("/error").permitAll()

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )

                // JWT authentication filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
