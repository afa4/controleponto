package com.example.controleponto.exception.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//} catch (DateTimeParseException e) {
//        return ResponseEntity.status(BAD_REQUEST).body(new MessageResp("Data e hora em formato inválido"));
//        } catch (CompletedWorkdayException e) {
//        return ResponseEntity.status(FORBIDDEN).body(new MessageResp(e.getMessage()));
//        }
}
