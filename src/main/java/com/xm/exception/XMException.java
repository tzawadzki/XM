package com.xm.exception;

public class XMException extends RuntimeException {

    public XMException(Throwable cause) {
        super(cause);
    }

    public XMException(String message) {
        super(message);
    }
}
