package com.example.controleponto.exception;

public class InvalidYearMonthFormatException extends RuntimeException {
    public InvalidYearMonthFormatException() {
        super("Parâmetro com formato inválido.");
    }
}
