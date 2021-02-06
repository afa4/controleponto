package com.example.controleponto.exception;

public class ForbiddenRegisterException extends RuntimeException {
    public ForbiddenRegisterException() {
        super("Não é possivel inserir este registro.");
    }
}
