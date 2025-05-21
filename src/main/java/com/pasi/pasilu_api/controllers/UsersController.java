package com.pasi.pasilu_api.controllers;

import com.pasi.pasilu_api.dtos.users.UserRegistrationRequest;
import com.pasi.pasilu_api.dtos.users.UserRegistrationResponse;
import com.pasi.pasilu_api.dtos.users.UserResponse;
import com.pasi.pasilu_api.dtos.users.UserUpdateRequest;
import com.pasi.pasilu_api.services.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService userService;

    /* ---------- Crear ---------- */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegistrationResponse register(@Valid @RequestBody UserRegistrationRequest body) {
        return userService.register(body);
    }

    /* ---------- Consultar todos ---------- */
    @GetMapping
    public List<UserResponse> getAll() {
        return userService.findAll();
    }

    /* ---------- Consultar por ID ---------- */
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable UUID id) {
        return userService.findById(id);
    }

    /* ---------- Actualizar ---------- */
    @PutMapping("/{id}")
    public UserResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequest body) {
        return userService.update(id, body);
    }
}
