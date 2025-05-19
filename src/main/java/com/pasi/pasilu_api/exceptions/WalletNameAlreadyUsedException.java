package com.pasi.pasilu_api.exceptions;

public class WalletNameAlreadyUsedException extends RuntimeException {
    public WalletNameAlreadyUsedException(String name) {
        super("Ya existe una wallet con nombre '" + name + "'");
    }
}
