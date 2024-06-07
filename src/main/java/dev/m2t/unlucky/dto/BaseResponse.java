package dev.m2t.unlucky.dto;

import java.time.Instant;

public class BaseResponse<T> {
    private final Instant timestamp;
    private final T data;
    private final boolean isSuccess;
    private final String message;
    private boolean showNotification;

    public BaseResponse(String message, boolean isSuccess, boolean showNotification, T data) {
        this.timestamp = Instant.now();
        this.message = message;
        this.isSuccess = isSuccess;
        this.showNotification = showNotification;
        this.data = data;
    }

    public BaseResponse(String message, boolean isSuccess, boolean showNotification) {
        this.timestamp = Instant.now();
        this.data = null;
        this.isSuccess = isSuccess;
        this.message = message;
        this.showNotification = showNotification;
    }

    // Getters
    public Instant getTimestamp() {
        return timestamp;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public boolean isShowNotification() {
        return showNotification;
    }

    public void setShowNotification(boolean showNotification) {
        this.showNotification = showNotification;
    }
}
