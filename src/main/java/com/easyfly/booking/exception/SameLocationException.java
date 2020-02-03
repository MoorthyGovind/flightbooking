package com.easyfly.booking.exception;

public class SameLocationException extends Exception {

	private static final long serialVersionUID = 1L;

	public SameLocationException(String message) {
		super(message);
	}
}
