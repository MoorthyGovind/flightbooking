package com.easyfly.booking.dto;

import java.util.List;

import com.easyfly.booking.common.BookingEnum.PaymentType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRequestDto{

	private Integer flightScheduleId;
	private String emailId;
	private Long phoneNumber;
	private Integer noOfPassengers;
	private PaymentType paymentType;
	private double totalFare;
	private List<PassengerDto> passagerList;
}
