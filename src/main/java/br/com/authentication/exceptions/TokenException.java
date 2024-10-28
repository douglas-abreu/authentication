package br.com.authentication.exceptions;

public class TokenException extends RuntimeException {

    public TokenException(String message) {
        super(message);
    }
}
