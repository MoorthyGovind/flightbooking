package com.easyfly.booking.service;

import java.time.format.DateTimeParseException;

import com.easyfly.booking.dto.SearchDto;
import com.easyfly.booking.dto.SearchResponseDto;
import com.easyfly.booking.exception.InvalidLocationException;
import com.easyfly.booking.exception.SameLocationException;

public interface FlightService {

	public SearchResponseDto getAvailableFlights(SearchDto searchDto)
			throws InvalidLocationException, SameLocationException;
}
