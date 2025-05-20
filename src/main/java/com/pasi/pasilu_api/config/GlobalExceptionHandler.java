package com.pasi.pasilu_api.config;

import com.pasi.pasilu_api.dtos.ApiError;
import com.pasi.pasilu_api.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice          // @ControllerAdvice + @ResponseBody
public class GlobalExceptionHandler {

    /* ---------- 404 ---------- */
    @ExceptionHandler({
            UserNotFoundException.class,
            RoleNotFoundException.class,
            PermissionNotFoundException.class,
            WalletNotFoundException.class,
            GoalNotFoundException.class,
            WalletMemberNotFoundException.class,
            TransactionNotFoundException.class,
            ApprovalNotFoundException.class
    })
    public ResponseEntity<ApiError> handleNotFound(RuntimeException ex) {
        return build(HttpStatus.NOT_FOUND, ex, "RESOURCE_NOT_FOUND");
    }

    /* ---------- 409 ---------- */
    @ExceptionHandler({
            EmailAlreadyUsedException.class,
            RoleNameAlreadyUsedException.class,
            PermissionNameAlreadyUsedException.class,
            WalletNameAlreadyUsedException.class,
            GoalAlreadyExistsException.class,
            WalletMemberAlreadyExistsException.class,
            RolePermissionAlreadyExistsException.class
    })
    public ResponseEntity<ApiError> handleConflict(RuntimeException ex) {
        return build(HttpStatus.CONFLICT, ex, "CONFLICT");
    }

    /* ---------- 400 validación Bean Validation ---------- */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {

        String msg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return build(HttpStatus.BAD_REQUEST, msg, "VALIDATION_ERROR");
    }

    /* ---------- 400 violación Constraint del trigger saldo ---------- */
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiError> handleBalance(InsufficientBalanceException ex) {
        return build(HttpStatus.BAD_REQUEST, ex, "INSUFFICIENT_BALANCE");
    }

    /* ---------- 400 Bean Validation en entidades (persist time) ---------- */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleEntityValidation(ConstraintViolationException ex) {
        String msg = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));
        return build(HttpStatus.BAD_REQUEST, msg, "ENTITY_VALIDATION_ERROR");
    }

    /* ---------- 400 duplicados de clave DB/límites de FK, etc. ---------- */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleIntegrity(DataIntegrityViolationException ex) {
        log.error("DB integrity violation", ex);
        return build(HttpStatus.BAD_REQUEST, "INTEGRITY_VIOLATION",
                "Operación no permitida por restricciones de base de datos");
    }

    /* ---------- Cualquier otro error no controlado ---------- */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, WebRequest req) {
        log.error(
                "Unhandled exception at {} {}",
                req.getDescription(false),
                ex.getMessage(),
                ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "UNEXPECTED_ERROR", ex.getMessage() );
    }

    /* ---------- Helpers ---------- */
    private ResponseEntity<ApiError> build(HttpStatus status, RuntimeException ex, String code) {
        return build(status, code, ex.getMessage());
    }
    private ResponseEntity<ApiError> build(HttpStatus status, String code, String message) {
        return ResponseEntity.status(status).body(new ApiError(status, code, message));
    }

    //Bad credentials
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String,String> handleBadCredentials(BadCredentialsException ex) {
        return Map.of("error", ex.getMessage());
    }
}
