package com.cts.helper;

/**
 * Response class for successful response
 *
 */
public class CourseResponse {
	// Status code of response
	private int statusCode;
	
	//Status message of response
	private String statusMessage;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	

}
