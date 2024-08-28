package com.ericsson.eniq.taf.db.utils;

/**
 * This is the custom exception that is thrown by all the MySqlDBHandler  methods.
 */
public class DBHandlerException extends Exception {
    private static final long serialVersionUID = -6067212927577741244L;

    /**
     * 
     * @param message error message
     */
    public DBHandlerException(final String message) {
        super(message);
    }

    /**
     * 
     * @param cause cause of error
     */
    public DBHandlerException(final Throwable cause) {
        super(cause);
    }

    /**
     * 
     * @param message error message
     * @param cause cause of error
     */
    public DBHandlerException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
