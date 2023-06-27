package com.example.ProyectoIntegrador.exceptions;





public class EntityNotFound extends RuntimeException {

    public EntityNotFound(String message) {
        super(message);
    }

    public EntityNotFound(String message, Throwable cause) {
        super(message, cause);
    }

}

