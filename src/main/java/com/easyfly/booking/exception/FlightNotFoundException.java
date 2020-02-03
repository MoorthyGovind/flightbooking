package com.easyfly.booking.exception;

public class FlightNotFoundException extends Exception {
	
	private static final long serialVersionUID = -6554420034407665916L;

	public FlightNotFoundException(String message) {
		super(message);
	}
}
