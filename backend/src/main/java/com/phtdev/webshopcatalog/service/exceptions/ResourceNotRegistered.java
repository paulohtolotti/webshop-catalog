package com.phtdev.webshopcatalog.service.exceptions;

public class ResourceNotRegistered extends RuntimeException {
    public ResourceNotRegistered(String message) {
        super(message);
    }
}
