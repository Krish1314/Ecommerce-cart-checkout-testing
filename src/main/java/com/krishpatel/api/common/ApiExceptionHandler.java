package com.krishpatel.api.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Global Exception Handler — catches exceptions thrown anywhere in the API
 * and converts them into clean JSON error responses.
 *
 * KEY CONCEPTS:
 *   @RestControllerAdvice → applies to ALL controllers; any exception thrown
 *                            in a controller method is caught here instead of
 *                            showing an ugly stack trace to the user.
 *
 * Without this handler, an invalid product id would return a 500 error
 * with a raw Java stack trace. With this, it returns a clean:
 *   { "error": "Product not found with id: 99" }  with HTTP 404
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * @ExceptionHandler → catches IllegalArgumentException specifically.
     * When ProductService.findById() throws this exception, Spring routes
     * it here automatically. We return a 404 NOT_FOUND with a JSON body.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }
}
