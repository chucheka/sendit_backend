package com.myapp.sendit.exception;

public class ActionNotAllowedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ActionNotAllowedException(String message) {
		super(message);
	}
}
