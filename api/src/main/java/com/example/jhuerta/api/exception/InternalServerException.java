package com.example.jhuerta.api.exception;

public class InternalServerException extends RuntimeException {

    public InternalServerException(String msg) {
        super(msg);
    }
}
