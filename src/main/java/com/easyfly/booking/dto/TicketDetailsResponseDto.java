package com.easyfly.booking.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDetailsResponseDto {

	private Integer ticketId;
	private LocalDate bookingDate;
	private String emailId;
	private Long phoneNumber;
	private String paymentType;
	private Double totalFare;
	private String status;
	private String source;
	private String destination;
	private List<PassengerDto> passengers;

}
