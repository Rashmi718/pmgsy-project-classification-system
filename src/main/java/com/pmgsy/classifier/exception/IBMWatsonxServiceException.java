package com.pmgsy.classifier.exception;

import org.springframework.http.HttpStatus;

public class IBMWatsonxServiceException extends RuntimeException {

    private final HttpStatus status;
    private final boolean timeout;

    public IBMWatsonxServiceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.timeout = false;
    }

    public IBMWatsonxServiceException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.timeout = false;
    }

    public IBMWatsonxServiceException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.GATEWAY_TIMEOUT;
        this.timeout = true;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public boolean isTimeout() {
        return timeout;
    }
}