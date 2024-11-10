package com.ebook_searching.deployment.exception;

import org.ebook_searching.common.exception.InvalidFieldsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler is a class that extends ResponseEntityExceptionHandler to provide centralized exception handling across all {@code @RequestMapping} methods through {@code @ExceptionHandler} methods.
 * <p>
 * This class is annotated with {@code @ControllerAdvice} which makes it applicable to all controllers in the application. It allows for global exception handling, common model attribute population, and so on, applicable to all {@code @RequestMapping} methods.
 * <p>
 * The main responsibility of this class is to handle exceptions that are not caught by the controller classes. It does this by overriding the methods from ResponseEntityExceptionHandler and adding new methods with the {@code @ExceptionHandler} annotation.
 * <p>
 * Each method in this class should be annotated with {@code @ExceptionHandler} and should have a single parameter: an exception type to be handled by the method. The methods can have flexible signatures, they may return a {@code ResponseEntity} or a {@code ModelAndView}, or they can be void.
 * <p>
 * Here is an example of how to define an exception handler method:
 * <pre>
 * {@code
 * \@ExceptionHandler(YourException.class)
 * public ResponseEntity<Object> handleYourException(YourException ex) {
 *     // Your handling logic goes here
 * }
 * }
 * </pre>
 * <p>
 * Remember to replace {@code YourException} with the type of exception you want to handle, and to implement your own handling logic in the method body.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    static final String DEFAULT_ERROR_NAME = "error";

    /**
     * Creates a map containing the error message of the given exception.
     *
     * @param errorName the key to use for the error message in the map
     * @param ex        the exception to get the error message from
     * @return a map containing the error message of the given exception
     */
    private Map<String, String> getError(String errorName, Exception ex) {
        Map<String, String> errors = new HashMap<>();
        String message = ex.getMessage();
        errors.put(errorName, message);
        return errors;
    }

    private Map<String, String> getError(String errorName, String message) {
        Map<String, String> errors = new HashMap<>();
        errors.put(errorName, message);
        return errors;
    }

    /**
     * Handles InvalidFieldsException by returning a ResponseEntity with a map of field errors and HttpStatus.BAD_REQUEST.
     * InvalidFieldsException is thrown when the server finds invalid fields in a request, typically indicating that the request does not meet certain validation criteria.
     *
     * @return a ResponseEntity with a map of field errors and HttpStatus.BAD_REQUEST
     */
    @ExceptionHandler(InvalidFieldsException.class)
    public ResponseEntity<Object> handleInvalidFieldsException(InvalidFieldsException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}


