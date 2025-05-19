package com.pasi.pasilu_api.exceptions;

/* Wrapper generico para el trigger de saldo insuficiente */
public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String msg) { super(msg); }
}