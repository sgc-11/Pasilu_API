package com.pasi.pasilu_api.config;

import com.pasi.pasilu_api.services.authentication.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;   // <-- inyecta el filtro

    /* ---------- Password ---------- */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* ---------- CORS global ---------- */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "https://midominio.com"
        ));
        cfg.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()        // pre-flight
        ));
        cfg.setAllowedHeaders(List.of("*"));      // Authorization, Content-Type, …
        cfg.setAllowCredentials(true);
        cfg.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", cfg);
        return source;
    }

    /* ---------- Security filter chain ---------- */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                /* --- sin estados y sin CSRF para API REST --- */
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                /* --- CORS: usa el bean anterior --- */
                .cors(Customizer.withDefaults())

                /* --- manejo de excepciones (401 por defecto) --- */
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                (req, res, e) -> res.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"))
                )

                /* --- reglas de autorización --- */
                .authorizeHttpRequests(auth -> auth
                        /* público: login, registro, docs */
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/register").permitAll()
                        /* opcional: monitorización /swagger */
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        /* todo lo demás requiere token válido */
                        .anyRequest().authenticated()
                )

                /* --- filtro JWT antes del UsernamePasswordAuthenticationFilter --- */
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
