package com.example.controleponto.exception.handler;

import com.example.controleponto.entity.dto.MessageResp;
import com.example.controleponto.exception.CompletedWorkdayException;
import com.example.controleponto.exception.TimeRegisterExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DateTimeParseException.class})
    public ResponseEntity<Object> handleDateTimeParseException(
            DateTimeParseException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResp("Data e hora em formato inválido"));
    }

    @ExceptionHandler({CompletedWorkdayException.class})
    public ResponseEntity<Object> handleCompletedWorkdayException(
            CompletedWorkdayException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new MessageResp(ex.getMessage()));
    }

    @ExceptionHandler({TimeRegisterExistsException.class})
    public ResponseEntity<Object> handleTimeRegisterExistsException(
            TimeRegisterExistsException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new MessageResp(ex.getMessage()));
    }
}
