package com.abimael.deviceresources.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseException extends RuntimeException {
    public DatabaseException(String resourceName) {
        super(String.format("Failed to modify %s", resourceName));
    }
}
