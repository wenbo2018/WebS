package com.tinymvc.exception;


public class ResourceLocationException extends RuntimeException {

    private static final long serialVersionUID = 1112368490033399874L;

    protected ResourceLocationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ResourceLocationException(Throwable cause) {
        super(cause);
    }

    public ResourceLocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceLocationException(String message) {
        super(message);
    }

    public ResourceLocationException() {
        super();
    }
}
