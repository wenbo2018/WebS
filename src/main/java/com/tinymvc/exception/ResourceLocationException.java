package com.tinymvc.exception;

/**
 * 常规异常
 * @author fck
 */
public class ResourceLocationException extends RuntimeException {

    private static final long serialVersionUID = 1112368490033399874L;

    public ResourceLocationException() {
        super();
    }

    public ResourceLocationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ResourceLocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceLocationException(String message) {
        super(message);
    }

    public ResourceLocationException(Throwable cause) {
        super(cause);
    }

}
