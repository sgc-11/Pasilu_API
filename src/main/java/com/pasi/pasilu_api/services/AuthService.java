package com.pasi.pasilu_api.services;

import com.pasi.pasilu_api.dtos.login.LoginRequest;
import com.pasi.pasilu_api.dtos.login.LoginResponse;
import com.pasi.pasilu_api.entities.User;
import com.pasi.pasilu_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;   // ya existe

    public LoginResponse login(LoginRequest req) {
        User user = userRepo.findUserByMail(req.mail())
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        if (!encoder.matches(req.password(), user.getPasswordHash()))
            throw new BadCredentialsException("Credenciales inválidas");

        return new LoginResponse(
                user.getId(), user.getName(), user.getLastname(), user.getMail());
    }
}

