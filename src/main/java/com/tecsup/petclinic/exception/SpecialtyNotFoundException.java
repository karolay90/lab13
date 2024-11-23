package com.tecsup.petclinic.exception;

public class SpecialtyNotFoundException extends RuntimeException {

    public SpecialtyNotFoundException(String message) {
        super(message);
    }
}
