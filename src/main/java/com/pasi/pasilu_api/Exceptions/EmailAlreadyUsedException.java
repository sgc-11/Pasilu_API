package com.pasi.pasilu_api.Exceptions;

public class EmailAlreadyUsedException extends RuntimeException {
  public EmailAlreadyUsedException(String message) {
    super("El email '" + message + "' ya está en uso");
  }
}
