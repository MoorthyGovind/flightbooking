package com.easyfly.booking.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FlightDto {
	
	private Integer flightId;
	private String flightName;
	private Integer flightScheduleId;
	private LocalDate scheduleDate;
	private Integer availableSeats;
	private String source;
	private String destination;
	private Double fare;

}
