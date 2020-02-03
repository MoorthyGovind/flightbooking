package com.easyfly.booking.exception;

public class CancelTicketBeforeRangeException extends Exception {

	private static final long serialVersionUID = 1L;

	public CancelTicketBeforeRangeException(String message) {
		super(message);
	}
}
