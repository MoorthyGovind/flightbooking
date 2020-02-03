package com.easyfly.booking.exception;

public class InvalidLocationException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidLocationException(String message) {
		super(message);
	}
}
