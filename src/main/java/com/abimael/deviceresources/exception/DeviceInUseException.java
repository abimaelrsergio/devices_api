package com.abimael.deviceresources.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class DeviceInUseException extends RuntimeException {
    public DeviceInUseException(String resourceName, String fieldName, Long fieldValue) {
        super(String.format("%s with the given input %s: '%s', is in use, and cannot be modified", resourceName, fieldName, fieldValue));
    }
}
