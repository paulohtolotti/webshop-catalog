package com.phtdev.webshopcatalog.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO extends BaseErrorDTO {

    private final List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public ValidationErrorDTO(String error, int status, String path, Instant timestamp) {
        super(error, status, path, timestamp);
    }

    public void addFieldError(String field, String message) {
        fieldErrors.add(new FieldErrorDTO(field, message));
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }
}
