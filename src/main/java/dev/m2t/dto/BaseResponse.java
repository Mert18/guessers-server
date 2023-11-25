package dev.m2t.dto;

import java.time.Instant;

public class BaseResponse<T> {
    private final Instant timestamp;
    private final T data;
    private final int code;
    private final boolean isSuccess;
    private final String message;

    // Constructor for success response
    public BaseResponse(T data, int code, String message) {
        this.timestamp = Instant.now();
        this.data = data;
        this.code = code;
        this.isSuccess = true;
        this.message = message;
    }

    public BaseResponse(int code, String message, boolean isSuccess) {
        this.timestamp = Instant.now();
        this.data = null;
        this.code = code;
        this.isSuccess = isSuccess;
        this.message = message;
    }

    // Getters
    public Instant getTimestamp() {
        return timestamp;
    }

    public T getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }
}
