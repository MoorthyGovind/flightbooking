package com.easyfly.booking.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.easyfly.booking.common.BookingEnum;
import com.easyfly.booking.dto.PassengerDto;
import com.easyfly.booking.dto.TicketDetailsResponseDto;
import com.easyfly.booking.dto.TicketRequestDto;
import com.easyfly.booking.entity.Flight;
import com.easyfly.booking.entity.FlightSchedule;
import com.easyfly.booking.entity.Location;
import com.easyfly.booking.entity.Passenger;
import com.easyfly.booking.entity.Ticket;
import com.easyfly.booking.exception.CancelTicketBeforeRangeException;
import com.easyfly.booking.exception.FlightNotFoundException;
import com.easyfly.booking.exception.PassengerNotFoundException;
import com.easyfly.booking.exception.TicketNotFoundException;
import com.easyfly.booking.repository.FlightScheduleRepository;
import com.easyfly.booking.repository.PassengerRepository;
import com.easyfly.booking.repository.TicketRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TicketServiceTest {

	@InjectMocks
	TicketServiceImpl ticketServiceImpl;

	@Mock
	TicketRepository ticketRepository;

	@Mock
	PassengerRepository passengerRepository;

	@Mock
	FlightScheduleRepository flightScheduleRepository;

	Location location = new Location();
	Ticket ticket = new Ticket();
	TicketDetailsResponseDto ticketDetailsResponseDto = new TicketDetailsResponseDto();
	Passenger passenger = new Passenger();
	List<Passenger> passengers = new ArrayList<>();
	PassengerDto passengerDto = new PassengerDto();
	List<PassengerDto> passengerDtos = new ArrayList<>();
	Flight flight = new Flight();
	FlightSchedule flightSchedule = new FlightSchedule();
	TicketRequestDto ticketRequestDto= new TicketRequestDto();

	@Before
	public void setUp() {
		location.setLocationId(1);
		location.setLocationName("Bangalore");

		passenger.setTicketId(ticket);
		passenger.setName("priya");
		passengers.add(passenger);
		flight.setFlightId(1);
		flight.setFlightName("indigo");
		flight.setSourceId(location);
		flight.setDestinationId(location);
		flightSchedule.setFlightScheduleId(1);
		flightSchedule.setFlightId(flight);
		flightSchedule.setFlightScheduledDate(LocalDate.of(2020, 02, 05));
		flightSchedule.setAvailableSeats(10);

		ticket.setFlightScheduleId(flightSchedule);
		ticket.setBookingDate(LocalDate.now());
		ticket.setTicketId(111L);
		ticket.setPhoneNumber(123456L);
		ticket.setStatus("Booked");
		ticket.setEmailId("moorthy127@gmail.com");

		ticketDetailsResponseDto.setTicketId(111L);
		passengerDto.setName(passenger.getName());
		passengerDtos.add(passengerDto);
		ticketDetailsResponseDto.setFlightName("Air Asia");
		ticketDetailsResponseDto.setPassengers(passengerDtos);
		ticketDetailsResponseDto.setPhoneNumber(ticket.getPhoneNumber());
		ticketDetailsResponseDto.setSource(ticket.getStatus());
		
		ticketRequestDto.setFlightScheduleId(1);
		ticketRequestDto.setNoOfPassengers(12);
		ticketRequestDto.setTotalFare(1500);
		ticketRequestDto.setFlightScheduleId(1);
		ticketRequestDto.setPaymentType(BookingEnum.PaymentType.PAYTM);
	}

	@Test
	public void testGetTicketDetails() throws TicketNotFoundException, PassengerNotFoundException {
		Mockito.when(ticketRepository.findById(111L)).thenReturn(Optional.of(ticket));
		Mockito.when(passengerRepository.findAllByTicketId(ticket)).thenReturn(passengers);
		TicketDetailsResponseDto actual = ticketServiceImpl.getTicketDetails(111L);
		assertEquals("indigo", actual.getFlightName());
	}

	@Test(expected = PassengerNotFoundException.class)
	public void testGetTicketDetailsForPassengerNotFoundException()
			throws TicketNotFoundException, PassengerNotFoundException {
		passengers = new ArrayList<>();
		Mockito.when(ticketRepository.findById(111L)).thenReturn(Optional.of(ticket));
		Mockito.when(passengerRepository.findAllByTicketId(ticket)).thenReturn(passengers);
		ticketServiceImpl.getTicketDetails(111L);
	}

	@Test(expected = TicketNotFoundException.class)
	public void testGetTicketDetailsForTicketNotFound() throws TicketNotFoundException, PassengerNotFoundException {
		Mockito.when(ticketRepository.findById(1141L)).thenReturn(Optional.ofNullable(null));
		ticketServiceImpl.getTicketDetails(1141L);
	}

	@Test
	public void testCancleBooking()
			throws TicketNotFoundException, PassengerNotFoundException, CancelTicketBeforeRangeException {
		Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
		Mockito.when(passengerRepository.findAllByTicketId(ticket)).thenReturn(passengers);
		Mockito.when(flightScheduleRepository.findById(1)).thenReturn(Optional.of(flightSchedule));
		ticketServiceImpl.cancelTicket(1L);
		assertEquals("moorthy127@gmail.com", ticket.getEmailId());
	}
	
	@Test(expected = TicketNotFoundException.class)
	public void testCancleBookingForTicketNotFoundException()
			throws TicketNotFoundException, PassengerNotFoundException, CancelTicketBeforeRangeException {
		Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
		ticketServiceImpl.cancelTicket(1L);
	}
	
	@Test(expected = PassengerNotFoundException.class)
	public void testCancleBookingForPassengerNotFoundException()
			throws TicketNotFoundException, PassengerNotFoundException, CancelTicketBeforeRangeException {
		List<Passenger> passengers =  new ArrayList<>();
		Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
		Mockito.when(passengerRepository.findAllByTicketId(ticket)).thenReturn(passengers);
		ticketServiceImpl.cancelTicket(1L);
	}
	
	@Test(expected = CancelTicketBeforeRangeException.class)
	public void testCancleBookingForCancelTicketBeforeRangeException()
			throws TicketNotFoundException, PassengerNotFoundException, CancelTicketBeforeRangeException {
		flightSchedule.setFlightScheduledDate(LocalDate.of(2020, 02, 04));
		Mockito.when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
		Mockito.when(passengerRepository.findAllByTicketId(ticket)).thenReturn(passengers);
		Mockito.when(flightScheduleRepository.findById(1)).thenReturn(Optional.of(flightSchedule));
		ticketServiceImpl.cancelTicket(1L);
	}

	@Test(expected = FlightNotFoundException.class)
	public void testReserveTicketFlightNotFoundException() throws FlightNotFoundException, NamingException{
		Mockito.when(flightScheduleRepository.findById(2)).thenReturn(Optional.of(flightSchedule));
		ticketServiceImpl.reserveTicket(ticketRequestDto);
	}
	
	@Test(expected = FlightNotFoundException.class)
	public void testReserveInsufficientSeats() throws FlightNotFoundException, NamingException{
		Mockito.when(flightScheduleRepository.findById(1)).thenReturn(Optional.of(flightSchedule));
		ticketServiceImpl.reserveTicket(ticketRequestDto);
	}
	
}
