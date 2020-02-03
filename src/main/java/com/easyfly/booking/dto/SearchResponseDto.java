package com.easyfly.booking.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResponseDto {

	private Integer statusCode;
	private String message;
	private List<FlightDto> flightList;
}
