package com.example.controleponto.exception;

public class CompletedWorkdayException extends RuntimeException {
    public CompletedWorkdayException() {
        super("Apenas 4 horários podem ser registrados por dia.");
    }
}
