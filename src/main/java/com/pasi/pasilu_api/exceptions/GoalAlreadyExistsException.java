package com.pasi.pasilu_api.exceptions;

public class GoalAlreadyExistsException extends RuntimeException {
    public GoalAlreadyExistsException(String walletId) {
        super("La wallet " + walletId + " ya tiene una meta");
    }
}
