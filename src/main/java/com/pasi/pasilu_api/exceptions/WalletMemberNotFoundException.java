package com.pasi.pasilu_api.exceptions;

public class WalletMemberNotFoundException extends RuntimeException {
    public WalletMemberNotFoundException(String memberId) {
        super("Miembro con id " + memberId + " no encontrado");
    }
}
