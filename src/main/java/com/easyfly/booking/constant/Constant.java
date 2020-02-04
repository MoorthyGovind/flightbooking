package com.easyfly.booking.constant;

public class Constant {

	public static final String LOCATION_NOT_FOUND = "Location not found";
	public static final String TICKET_NOT_FOUND = "Invalid TicketId";
	public static final String PASSENGER_NOT_FOUND = "Passenger not found";

	public static final String SUCCESS = "SUCCESS";

	// Flights
	public static final String INVALID_SOURCE_LOCATION = "Invalid Source Location";
	public static final String INVALID_DESTINATION_LOCATION = "Invalid Destination Location";
	public static final String SOURCE_DESTINATION_SHOULD_NOT_SAME = "Source and Destination should not be same.";
	
	//Validation
	public static final String SOURCE_EMPTY = "source should not be empty";
	public static final String DESTINATION_EMPTY = "destination should not be empty";
	public static final String NO_OF_PASSENGERS_LESS_THAN_1 = "noOfPassengers can't be less than 1";
	public static final String TRAVEL_TYPE_EMPTY = "travelType should not be empty";
	public static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd";


	// Tickets
	public static final String TICKET_BOOKING_CANCELLED = "Cancelled";
	public static final String TICKET_CANCELLED_SUCCESSFULLY = "Ticket Cancelled Successfully";
	public static final String TICKET_CANCELLED_BEFORE_RANGE = "Cannot cancel ticket, you can cancel your ticket before one day from travel date.";

	private Constant() {

	}

	public static final String FLIGHT_NOT_FOUND = "Flight Not Found";
	public static final String FAILURE_MESSAGE = "Failure";
	public static final String SUCCESS_MESSAGE = "Success";
	public static final String STATUS_BOOKED = "BOOKED";
	public static final String INSUFFICIENT_TICKETS = "Insufficient tickets available";
	
	public static final String PHONEPE_MSG = "Booking successful..Payment succedded using Phonepe";
	public static final String PAYTM_MSG = "Booking successful..Payment succedded using PayTm";
}
