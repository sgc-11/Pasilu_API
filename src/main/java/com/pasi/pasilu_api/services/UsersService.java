package com.pasi.pasilu_api.services;

import com.pasi.pasilu_api.dtos.users.UserRegistrationResponse;
import com.pasi.pasilu_api.entities.User;
import com.pasi.pasilu_api.exceptions.EmailAlreadyUsedException;
import com.pasi.pasilu_api.exceptions.UserNotFoundException;
import com.pasi.pasilu_api.dtos.users.UserRegistrationRequest;
import com.pasi.pasilu_api.dtos.users.UserResponse;
import com.pasi.pasilu_api.dtos.users.UserUpdateRequest;
import com.pasi.pasilu_api.repositories.UserRepository;
import com.pasi.pasilu_api.services.authentication.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /* -------------QUERY -------------*/
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(UUID id) {
        return userRepo.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /* -------  POST --------*/
    @Transactional
    public UserRegistrationResponse register(UserRegistrationRequest dto) {
        if (userRepo.existsByMail(dto.mail()))
            throw new EmailAlreadyUsedException(dto.mail());

        User user = new User();
        user.setName(dto.name());
        user.setLastname(dto.lastname());
        user.setMail(dto.mail());
        user.setCellphone(dto.cellphone());
        user.setLocation(dto.location());
        user.setPasswordHash(passwordEncoder.encode(dto.password()));
        user.setCreatedAt(java.time.LocalDateTime.now());

        userRepo.save(user);

        String token = jwtService.generateToken(user.getId());

        return new UserRegistrationResponse(
                user.getId(), user.getName(), user.getLastname(), user.getMail(), token
        );
    }

    /* -------  UPDATE --------*/
    @Transactional
    public UserResponse update(UUID id, @Valid UserUpdateRequest dto) {
    User user = userRepo.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

    if(dto.name() != null) user.setName(dto.name());
    if(dto.lastname() != null) user.setLastname(dto.lastname());
    if(dto.cellphone() != null) user.setCellphone(dto.cellphone());
    if(dto.location() != null) user.setLocation(dto.location());
    if(dto.password() != null && !dto.password().isBlank()) user.setPasswordHash(passwordEncoder.encode(dto.password()));

    //No permitido cambiar el mail
    return mapToResponse(user);
}

    private UserResponse mapToResponse(User u) {
        return new UserResponse(
                u.getId(), u.getName(), u.getLastname(),
                u.getMail(), u.getCellphone(), u.getLocation(), u.getCreatedAt()
        );
    }

}
