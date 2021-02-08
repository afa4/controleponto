package com.example.controleponto.exception;

public class TimeRegisterExistsException extends RuntimeException {
    public TimeRegisterExistsException() {
        super("Horário já registrado.");
    }
}
