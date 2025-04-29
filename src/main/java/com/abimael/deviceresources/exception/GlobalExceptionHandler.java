package com.abimael.deviceresources.exception;

import com.abimael.deviceresources.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;

/**
 * Global exception handler for handling application-wide exceptions and returning standardized error responses.
 * Author: Abimael R Sergio
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle all unhandled exceptions and return a JSON response containing information about the error.
     * @param exception the exception to be handled
     * @param webRequest the current web request
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGlobalException(Exception exception, WebRequest webRequest) {
        ErrorDto errorDto = new ErrorDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle all IllegalArgumentExceptions and return a JSON response containing information about the error.
     * @param exception the exception to be handled
     * @param webRequest the current web request
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest webRequest) {
        ErrorDto errorDto = new ErrorDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle ResourceNotFoundException and return a JSON response containing information about the error.
     * @param exception the exception to be handled
     * @param webRequest the current web request
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                    WebRequest webRequest) {
        ErrorDto errorDTO = new ErrorDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle DeviceInUseException and return a JSON response containing information about the error.
     * @param exception the exception to be handled
     * @param webRequest the current web request
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(DeviceInUseException.class)
    public ResponseEntity<ErrorDto> handleDeviceInUseException(DeviceInUseException exception,
                                                                    WebRequest webRequest) {
        ErrorDto errorDTO = new ErrorDto(
                webRequest.getDescription(false),
                HttpStatus.FORBIDDEN,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
    }
}
