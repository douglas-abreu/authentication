package br.com.authentication.exceptions;

public class RequestLoginException extends RuntimeException {

    public RequestLoginException(String message) {
        super(message);
    }
}
