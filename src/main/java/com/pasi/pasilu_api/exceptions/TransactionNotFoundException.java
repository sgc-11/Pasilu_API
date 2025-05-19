package com.pasi.pasilu_api.exceptions;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String id) { super("Transacción " + id + " no encontrada"); }
}
