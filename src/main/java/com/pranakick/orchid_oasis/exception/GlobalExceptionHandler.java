package com.pranakick.orchid_oasis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ValidationException and returns a response with a map containing the error message and the validation errors.
     * @param ex the ValidationException
     * @return a response entity with the error message and the validation errors
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        Map<String, String> errorResponse = Map.of(
                "message", "Validation errors",
                "errors", errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles BookNotFoundExeption and returns a response with a map containing the error message.
     * @param ex the BookNotFoundExeption
     * @return a response entity with the error message
     */
    @ExceptionHandler(BookNotFoundExeption.class)
    public ResponseEntity<Map<String, String>> handleBookNotFound(BookNotFoundExeption ex) {
        Map<String, String> errorResponse = Map.of("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}