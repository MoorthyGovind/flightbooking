package com.easyfly.booking.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.easyfly.booking.constant.Constant;
import com.easyfly.booking.dto.ResponseDto;
import com.easyfly.booking.dto.TicketDetailsResponseDto;
import com.easyfly.booking.dto.TicketRequestDto;
import com.easyfly.booking.dto.TicketResponsedto;
import com.easyfly.booking.entity.Passenger;
import com.easyfly.booking.entity.Ticket;
import com.easyfly.booking.exception.CancelTicketBeforeRangeException;
import com.easyfly.booking.exception.FlightNotFoundException;
import com.easyfly.booking.exception.PassengerNotFoundException;
import com.easyfly.booking.exception.TicketNotFoundException;
import com.easyfly.booking.service.TicketService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TicketControllerTest {

	@InjectMocks
	TicketController ticketController;

	@Mock
	TicketService ticketService;

	Ticket ticket = new Ticket();
	TicketDetailsResponseDto ticketDetailsResponseDto = new TicketDetailsResponseDto();
	Passenger Passenger = new Passenger();
	TicketRequestDto ticketRequestDto= new TicketRequestDto();
	TicketResponsedto ticketResponsedto= new TicketResponsedto();

	@Before
	public void setUp() {
		ticket.setBookingDate(LocalDate.now());
		ticket.setTicketId(111L);
		Passenger.setTicketId(ticket);
		ticketDetailsResponseDto.setTicketId(111L);
	}

	@Test
	public void testGetTicketDetails() throws TicketNotFoundException, PassengerNotFoundException {

		Mockito.when(ticketService.getTicketDetails(111L)).thenReturn(ticketDetailsResponseDto);
		ResponseEntity<TicketDetailsResponseDto> actual = ticketController.getTicketDetails(111L);
		assertEquals(HttpStatus.OK, actual.getStatusCode());

	}

	@Test(expected = TicketNotFoundException.class)
	public void testGetTicketDetailsForTicketNotFoundException()
			throws TicketNotFoundException, PassengerNotFoundException {

		ticket.setTicketId(null);
		ticketController.getTicketDetails(null);

	}

	@Test
	public void testCancelTicket()
			throws TicketNotFoundException, PassengerNotFoundException, CancelTicketBeforeRangeException {
		ticket.setTicketId(1L);
		ticketService.cancelTicket(1L);
		ResponseEntity<ResponseDto> response = ticketController.cancelTicket(1L);
		assertEquals(Constant.TICKET_CANCELLED_SUCCESSFULLY, response.getBody().getMessage());
	}
	
	@Test
	public void testReserveTicket() throws FlightNotFoundException, NamingException{
		Mockito.when(ticketService.reserveTicket(ticketRequestDto)).thenReturn(ticketResponsedto);
		ResponseEntity<TicketResponsedto> response=ticketController.reserveTicket(ticketRequestDto);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test(expected = TicketNotFoundException.class)
	public void testCancelTicketFor()
			throws TicketNotFoundException, PassengerNotFoundException, CancelTicketBeforeRangeException {
		ticket.setTicketId(null);
		ticketController.cancelTicket(null);
	}

}
