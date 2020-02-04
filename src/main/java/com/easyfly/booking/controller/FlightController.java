package com.easyfly.booking.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyfly.booking.constant.Constant;
import com.easyfly.booking.dto.SearchDto;
import com.easyfly.booking.dto.SearchResponseDto;
import com.easyfly.booking.exception.InvalidLocationException;
import com.easyfly.booking.exception.SameLocationException;
import com.easyfly.booking.service.FlightService;

/**
 * FlightController Class, Get the available flights based on the source,
 * destination, date and travel types.
 * 
 * @author Govindasamy.C
 * @since 03-02-2020
 *
 */
@RestController
@RequestMapping("/flights")
@CrossOrigin
public class FlightController {
	private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

	@Autowired
	FlightService flightService;

	/**
	 * Get the available flights based on the source, destination, date and travel
	 * type params
	 * 
	 * @param searchDto - finding a available flights based on the source,
	 *                  destination, date and travel type params.
	 * @return list of the flight details along with status code and message.
	 * @throws InvalidLocationException - throws the InvalidLocationException while
	 *                                  giving the invalid data the source and
	 *                                  destination locations.
	 * @throws SameLocationException    - throws the SameLocationException while
	 *                                  giving same date the source and destination
	 *                                  locations.
	 */
	@PostMapping
	public ResponseEntity<SearchResponseDto> getAvailableFlights(@Valid @RequestBody SearchDto searchDto)
			throws InvalidLocationException, SameLocationException {
		logger.info("get the available flights based on the search params.");
		SearchResponseDto responseDto = flightService.getAvailableFlights(searchDto);
		responseDto.setStatusCode(HttpStatus.OK.value());
		responseDto.setMessage(Constant.SUCCESS);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
}
