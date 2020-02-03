package com.easyfly.booking.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {

	@NotNull(message = "source should not be empty")
	private String source;

	@NotNull(message = "destination should not be empty")
	private String destination;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String date;

	@Min(value = 1, message = "noOfPassengers can't be less than 1")
	private Integer noOfPassengers;

	@NotNull(message = "travelType should not be empty")
	private String travelType;

}
