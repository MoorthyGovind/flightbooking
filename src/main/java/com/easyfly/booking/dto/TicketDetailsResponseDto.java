package com.easyfly.booking.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.easyfly.booking.common.BookingEnum.PaymentType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDetailsResponseDto {

	private Long ticketId;
	private LocalDate bookingDate;
	private String emailId;
	private Long phoneNumber;
	private PaymentType paymentType;
	private Double totalFare;
	private String status;
	private String source;
	private String destination;
	private LocalTime departureTime;
	private LocalTime arrivalTime;
	private List<PassengerDto> passengers;

}
