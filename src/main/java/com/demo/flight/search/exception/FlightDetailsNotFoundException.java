package com.demo.flight.search.exception;
/**
 * 
 * FlightDetailsNotFoundException
 *
 */
public class FlightDetailsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2519918219273516067L;
	/**
	 * Catches custom message for Flight Details Not Found Exception 
	 * @param msg : custom exception message
	 */
	public FlightDetailsNotFoundException(String msg) {
		super(msg);
	}
}