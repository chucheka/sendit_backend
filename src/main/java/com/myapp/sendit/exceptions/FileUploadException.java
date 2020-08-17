package com.myapp.sendit.exceptions;




public class FileUploadException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public FileUploadException() {
        super();
    }
    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
    public FileUploadException(String message) {
        super(message);
    }
    public FileUploadException(Throwable cause) {
        super(cause);
    }

}
