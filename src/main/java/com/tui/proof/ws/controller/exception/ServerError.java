package com.tui.proof.ws.controller.exception;

public enum ServerError {

    INPUT_INVALID(1000, "Input object is invalid"),
    OBJECT_NOT_FOUND(1001, "Object not found"),
    INTERNAL_SERVER_ERROR(5000, "Internal server error");

    private final int statusCode;
    private final String message;

    ServerError(int statusCode, String msg) {
        this.message = msg;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
