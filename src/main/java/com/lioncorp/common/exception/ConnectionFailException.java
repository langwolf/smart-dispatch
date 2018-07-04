package com.lioncorp.common.exception;


public class ConnectionFailException extends ThriftException {

    private static final long serialVersionUID = 4457437871618462115L;

    public ConnectionFailException() {
        super();
    }

    public ConnectionFailException(String message) {
        super(message);
    }

    public ConnectionFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
