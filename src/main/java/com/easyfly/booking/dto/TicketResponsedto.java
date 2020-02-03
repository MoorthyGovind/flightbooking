package com.easyfly.booking.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketResponsedto extends ResponseDto {
	private Long ticketId;
	private LocalDate bookingDate;
	private double totalFare;
}
