package com.project.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyPresentException.class)
    public ResponseEntity<Map<String, String>> handleResourceAlreadyPresentException(
            ResourceAlreadyPresentException ex, WebRequest request) {
        
        // Create a map to hold the JSON structure
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());

        // Return the map wrapped in a ResponseEntity
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
