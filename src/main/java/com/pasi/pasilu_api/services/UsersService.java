package com.pasi.pasilu_api.services;

import com.pasi.pasilu_api.Entities.User;
import com.pasi.pasilu_api.Exceptions.EmailAlreadyUsedException;
import com.pasi.pasilu_api.Exceptions.UserNotFoundException;
import com.pasi.pasilu_api.dtos.users.UserRegistrationRequest;
import com.pasi.pasilu_api.dtos.users.UserResponse;
import com.pasi.pasilu_api.dtos.users.UserUpdateRequest;
import com.pasi.pasilu_api.repositories.UserRepository;
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
    public UserResponse register(UserRegistrationRequest dto) {
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

        User saved = userRepo.save(user);

        return mapToResponse(saved);
    }

    /* -------  UPDATE --------*/
public UserResponse update(UUID id, @Valid UserUpdateRequest dto) {
    User user = userRepo.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

    user.setName(dto.name());
    user.setLastname(dto.lastname());
    user.setCellphone(dto.cellphone());
    user.setLocation(dto.location());

    if(dto.password() != null && !dto.password().isBlank()) {
        user.setPasswordHash(passwordEncoder.encode(dto.password()));
    }

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
