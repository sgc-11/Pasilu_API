package com.pasi.pasilu_api.exceptions;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(String id) {
        super("Wallet con id " + id + " no encontrada");
    }
}
