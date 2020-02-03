package com.easyfly.booking.exception;

public class TicketNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public TicketNotFoundException(String message) {
		super(message);
	}

}
