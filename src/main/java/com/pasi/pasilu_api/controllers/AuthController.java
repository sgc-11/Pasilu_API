package com.pasi.pasilu_api.controllers;

import com.pasi.pasilu_api.dtos.login.LoginRequest;
import com.pasi.pasilu_api.dtos.login.LoginResponse;
import com.pasi.pasilu_api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")   // cambia según tu host
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest req) {
        return authService.login(req);   // 200 OK con user data
    }
}

