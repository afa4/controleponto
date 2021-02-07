package com.example.controleponto.exception;

public class TimeRegisterForbiddenException extends RuntimeException {
    public TimeRegisterForbiddenException() {
        super("Não é possivel inserir este registro.");
    }
}
