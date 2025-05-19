package com.pasi.pasilu_api.exceptions;

public class WalletMemberAlreadyExistsException extends RuntimeException {
    public WalletMemberAlreadyExistsException(String walletId, String userId) {
        super("El usuario " + userId + " ya pertenece a la wallet " + walletId);
    }
}
