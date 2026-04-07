package com.phtdev.webshopcatalog.service.exceptions;

public class DatabaseViolationOccuredException extends RuntimeException {
    public DatabaseViolationOccuredException(String message) {
        super(message);
    }
}
