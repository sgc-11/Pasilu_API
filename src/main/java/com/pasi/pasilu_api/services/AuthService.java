package com.pasi.pasilu_api.services;

import com.pasi.pasilu_api.dtos.login.LoginRequest;
import com.pasi.pasilu_api.dtos.login.LoginResponse;
import com.pasi.pasilu_api.entities.User;
import com.pasi.pasilu_api.repositories.UserRepository;
import com.pasi.pasilu_api.services.authentication.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    /**
     * Valida las credenciales y devuelve el usuario si son correctas.
     */
    public User validateCredentials(LoginRequest req) {
        User user = userRepo.findUserByMail(req.mail())
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        if (!encoder.matches(req.password(), user.getPasswordHash()))
            throw new BadCredentialsException("Credenciales inválidas");

        return user;
    }

    /**
     * Método pensado para el /auth/login.  Devuelve los datos del usuario + JWT.
     */
    public LoginResponse login(LoginRequest req) {
        User user = validateCredentials(req);

        String token = jwtService.generateToken(user.getId());

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getMail(),
                token
        );
    }
}
