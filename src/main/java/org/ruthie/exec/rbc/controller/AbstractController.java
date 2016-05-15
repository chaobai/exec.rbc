package org.ruthie.exec.rbc.controller;

import org.ruthie.exec.rbc.service.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class AbstractController {

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource cannot be found")
    @ExceptionHandler({ ResourceNotFoundException.class })
    public void handleBasketNotFoundException(ResourceNotFoundException ex) {
    }
    
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad request.")
    @ExceptionHandler({ IllegalArgumentException.class })
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unexpected server error.")
    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex) {
        return ex.getMessage();
    }
}