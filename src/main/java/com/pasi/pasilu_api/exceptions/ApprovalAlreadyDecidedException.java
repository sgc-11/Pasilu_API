package com.pasi.pasilu_api.exceptions;

public class ApprovalAlreadyDecidedException extends RuntimeException {
    public ApprovalAlreadyDecidedException() { super("La aprobación ya fue decidida"); }
}
