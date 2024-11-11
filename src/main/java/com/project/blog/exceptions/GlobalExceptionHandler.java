package com.project.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.project.blog.payloads.ApiResponse;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyPresentException.class)
    public ResponseEntity<ApiResponse> handleResourceAlreadyPresentException(
            ResourceAlreadyPresentException ex, WebRequest request) {
        
        // Create a map to hold the JSON structure
//        Map<String, String> errorResponse = new HashMap<>();
//        errorResponse.put("message", ex.getMessage());
    	ApiResponse apiResponse = new ApiResponse(ex.getMessage(), false);

        // Return the map wrapped in a ResponseEntity
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
  
    	ApiResponse apiResponse =  new ApiResponse(ex.getMessage(), false);
    	return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    	
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
    	
    	Map<String, String> errorResponse = new HashMap<>();
    
    	 ex.getBindingResult().getFieldErrors().forEach((error) -> {
    	        String fieldName = error.getField();
    	        String errorMessage = error.getDefaultMessage();
    	        errorResponse.put(fieldName, errorMessage);
    	    });
    	
    	return new ResponseEntity<Map<String,String>>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
