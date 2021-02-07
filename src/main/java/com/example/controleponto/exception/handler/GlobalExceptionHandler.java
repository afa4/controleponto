package com.example.controleponto.exception.handler;

import com.example.controleponto.entity.dto.MessageResp;
import com.example.controleponto.exception.CompletedWorkdayException;
import com.example.controleponto.exception.TimeRegisterForbiddenException;
import com.example.controleponto.exception.TimeRegisterExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var endpoint = request.getDescription(false);
        if (endpoint.contains("/batidas")) {
            return errorMessage(HttpStatus.BAD_REQUEST, "Data e hora em formato inválido");
        } else {
            return super.handleHttpMessageNotReadable(ex, headers, status, request);
        }
    }

    @ExceptionHandler({CompletedWorkdayException.class, TimeRegisterForbiddenException.class})
    public ResponseEntity<Object> handleCompletedWorkdayException(
            RuntimeException ex, WebRequest request) {
        return errorMessage(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler({TimeRegisterExistsException.class})
    public ResponseEntity<Object> handleTimeRegisterExistsException(
            TimeRegisterExistsException ex, WebRequest request) {
        return errorMessage(HttpStatus.CONFLICT, ex.getMessage());
    }

    private ResponseEntity<Object> errorMessage(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new MessageResp(message));
    }

}
