package com.pasi.pasilu_api.repositories;

import com.pasi.pasilu_api.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByMail(@Email @NotBlank @Size(max = 100) String mail);
    Optional<User> findUserByMail(@Email @NotBlank String mail);
}