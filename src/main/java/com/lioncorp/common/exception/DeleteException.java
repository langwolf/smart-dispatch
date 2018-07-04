package com.lioncorp.common.exception;


public class DeleteException extends RuntimeException {

    private static final long serialVersionUID = 2580948893299977073L;

    public DeleteException() {
    }

    public DeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteException(String message) {
        super(message);
    }
}
