package com.pasi.pasilu_api.exceptions;

public class ApprovalNotFoundException extends RuntimeException {
    public ApprovalNotFoundException(String txId, String memberId) {
        super("No existe aprobación para tx " + txId + " y miembro " + memberId);
    }
}
