package br.com.authentication.exceptions;

public class SecretKeyNotFoundException extends RuntimeException {

    public SecretKeyNotFoundException(String message) {
        super(message);
    }
}
