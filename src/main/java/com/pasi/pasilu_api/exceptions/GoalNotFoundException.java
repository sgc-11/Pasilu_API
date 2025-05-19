package com.pasi.pasilu_api.exceptions;

public class GoalNotFoundException extends RuntimeException {
    public GoalNotFoundException(String walletId) {
        super("La wallet " + walletId + " no tiene meta asociada");
    }
}
