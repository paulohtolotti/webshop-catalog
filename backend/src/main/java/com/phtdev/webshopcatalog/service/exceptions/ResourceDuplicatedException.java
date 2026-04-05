package com.phtdev.webshopcatalog.service.exceptions;

public class ResourceDuplicatedException extends RuntimeException {
    public ResourceDuplicatedException(String message) {
        super(message);
    }
}
