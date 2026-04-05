package com.phtdev.webshopcatalog.dto;

import java.time.Instant;

public class BaseErrorDTO {
    private String error;
    private int status;
    private String path;
    private Instant timestamp;

    public BaseErrorDTO(String error, int status, String path, Instant timestamp) {
        this.error = error;
        this.status = status;
        this.path = path;
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
