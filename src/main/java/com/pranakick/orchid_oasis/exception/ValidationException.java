package com.pranakick.orchid_oasis.exception;

import org.springframework.validation.BindingResult;

public class ValidationException extends RuntimeException{
    private final BindingResult bindingResult;

    // Constructor that accepts a BindingResult object
    public ValidationException(BindingResult bindingResult) {
        super("Validation errors occurred");
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
