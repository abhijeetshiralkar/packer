package com.mobiquityinc.exception;

/**
 * Custom Exception to be thrown in case of incorrect input Data for packaging
 * 
 * @author abhijeetshiralkar
 *
 */
public class APIException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 5199629890017767754L;

	public APIException(String message, Throwable cause) {
		super(message, cause);
	}

	public APIException(String message) {
		super(message);
	}
}
