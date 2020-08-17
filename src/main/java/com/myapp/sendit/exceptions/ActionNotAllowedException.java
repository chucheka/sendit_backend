package com.myapp.sendit.exceptions;

public class ActionNotAllowedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    public ActionNotAllowedException() {
        super();
    }
    public ActionNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
    public ActionNotAllowedException(String message) {
        super(message);
    }
    public ActionNotAllowedException(Throwable cause) {
        super(cause);
    }

}
