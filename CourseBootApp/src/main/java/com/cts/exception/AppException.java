package com.cts.exception;

/**
 * Class to map application related exceptions
 *
 */
public class AppException extends Exception {

	private static final long serialVersionUID = -5142830261138971012L;

	/** 
	 * contains redundantly the HTTP status of the response sent back to the client in case of error, so that
	 * the developer does not have to look into the response headers. 
	 */
	Integer status;
	
	/** application specific error code */
	int code; 
	
	/**
	 * 
	 * @param status
	 * @param message
	 * @param developerMessage
	 */
	public AppException(int status, String message) {
		super(message);
		this.status = status;
	}

	public AppException() { }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
