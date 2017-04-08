package edu.itechart.contactlist.handler;

public class FieldHandlerException extends Exception {
    public FieldHandlerException() {
        super();
    }

    public FieldHandlerException(String message) {
        super(message);
    }

    public FieldHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldHandlerException(Throwable cause) {
        super(cause);
    }

    protected FieldHandlerException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
