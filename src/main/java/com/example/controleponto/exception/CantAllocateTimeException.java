package com.example.controleponto.exception;

public class CantAllocateTimeException extends RuntimeException {
    public CantAllocateTimeException() {
        super("Não se pode alocar horas para o dia informado.");
    }
}
