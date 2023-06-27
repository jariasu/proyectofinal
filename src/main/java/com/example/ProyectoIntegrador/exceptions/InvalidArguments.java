package com.example.ProyectoIntegrador.exceptions;

public class InvalidArguments extends RuntimeException {

    public InvalidArguments(String message) {
        super(message);
    }

    public InvalidArguments(String message, Throwable cause) {
        super(message, cause);
    }

}
