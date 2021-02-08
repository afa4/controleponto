package com.example.controleponto.exception;

public class TimeToAllocateBiggerThanWorkedTimeException extends RuntimeException {
    public TimeToAllocateBiggerThanWorkedTimeException() {
        super("Não pode alocar tempo maior que o tempo trabalhado no dia");
    }
}
