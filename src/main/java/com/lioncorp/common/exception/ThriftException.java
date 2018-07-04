package com.lioncorp.common.exception;


public class ThriftException extends RuntimeException {

    private static final long serialVersionUID = 2580948893299977073L;

    public ThriftException() {
    }

    public ThriftException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThriftException(String message) {
        super(message);
    }
}
