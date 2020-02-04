package com.easyfly.booking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.easyfly.booking.dto.SearchDto;
import com.easyfly.booking.dto.SearchResponseDto;
import com.easyfly.booking.exception.InvalidLocationException;
import com.easyfly.booking.exception.SameLocationException;
import com.easyfly.booking.service.FlightService;

@RunWith(MockitoJUnitRunner.class)
public class FlightControllerTest {

	@InjectMocks
	FlightController flightController;
	
	@Mock
	FlightService flightService;
	
	SearchDto searchDto = new SearchDto();
	
	@Before
	public void init() {
		searchDto.setSource("Coimbatore");
	}
	
	@Test
	public void testGetAvailableFlights() throws InvalidLocationException, SameLocationException {
		when(flightService.getAvailableFlights(searchDto)).thenReturn(new SearchResponseDto());
		ResponseEntity<SearchResponseDto> searchResponseDto = flightController.getAvailableFlights(searchDto);
		assertEquals(HttpStatus.OK.value(), searchResponseDto.getBody().getStatusCode());
	}

}
