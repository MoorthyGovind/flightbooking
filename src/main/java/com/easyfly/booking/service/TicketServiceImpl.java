package com.easyfly.booking.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyfly.booking.constant.Constant;
import com.easyfly.booking.dto.PassengerDto;
import com.easyfly.booking.dto.TicketRequestDto;
import com.easyfly.booking.dto.TicketResponsedto;
import com.easyfly.booking.entity.FlightSchedule;
import com.easyfly.booking.entity.Passenger;
import com.easyfly.booking.entity.Ticket;
import com.easyfly.booking.exception.FlightNotFoundException;
import com.easyfly.booking.repository.FlightScheduleRepository;
import com.easyfly.booking.repository.PassengerRepository;
import com.easyfly.booking.repository.TicketRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to perform the flight ticket related operation like
 * booking,cancellation
 * 
 * @author Chethana
 *
 */
@Service
@Slf4j
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	FlightScheduleRepository flightScheduleRepository;
	
	@Autowired
	PassengerRepository passengerRepository;
	
	/**
	 * This method is used to reserveTicket for one journey flight ticket
	 * 
	 * @author Chethana M
	 * @param ticketRequestDto - Takes parameters which are required to reserve ticket
	 * @throws FlightNotFoundException - Thrown when flight is not found
	 * @throws NamingException - Thrown when Payment Exception occurs
	 * @return TicketResponsedto - Returns Booking details
	 * 
	 */
	@Transactional
	public TicketResponsedto reserveTicket(TicketRequestDto ticketRequestDto) throws FlightNotFoundException, NamingException{
		log.info("Entering into reserveTicket of TicketServiceImpl");
		
		Optional<FlightSchedule> flightSchedule= flightScheduleRepository.findById(ticketRequestDto.getFlightScheduleId());
		if(!flightSchedule.isPresent()) {
			log.error("Exception Occured in reserveTicket of TicketServiceImpl:"+Constant.FLIGHT_NOT_FOUND);
			throw new FlightNotFoundException(Constant.FLIGHT_NOT_FOUND);
		}
		if(flightSchedule.get().getAvailableSeats()<ticketRequestDto.getNoOfPassengers()) {
			log.error("Exception Occured in reserveTicket of TicketServiceImpl:"+Constant.INSUFFICIENT_TICKETS);
			throw new FlightNotFoundException(Constant.INSUFFICIENT_TICKETS);
		}
		Ticket ticket= new Ticket();
		flightSchedule.get().setAvailableSeats(flightSchedule.get().getAvailableSeats()-ticketRequestDto.getNoOfPassengers());
		BeanUtils.copyProperties(ticketRequestDto, ticket);
		ticket.setStatus(Constant.STATUS_BOOKED);
		ticket.setTotalFare(ticketRequestDto.getTotalFare());
		ticket.setFlightScheduleId(flightSchedule.get());
		ticket.setPaymentType(ticketRequestDto.getPaymentType());
		ticket.setBookingDate(LocalDate.now());
		ticket=ticketRepository.save(ticket);
		PaymentService paymentService = ServiceLocator.getService(ticketRequestDto.getPaymentType().toString());
		paymentService.execute();
		final Ticket ticketDetails=ticket;

		
		List<PassengerDto> passengerList=ticketRequestDto.getPassagerList();
		passengerList.forEach(passengerIndex->{
			Passenger passenger= new Passenger();
			BeanUtils.copyProperties(passengerIndex, passenger);
			passenger.setTicketId(ticketDetails);
			passengerRepository.save(passenger);
		}
		);
	
		log.debug("Entering into reserveTicket of TicketServiceImpl:Ticket booked successfully");
		TicketResponsedto ticketResponsedto= new TicketResponsedto();
		BeanUtils.copyProperties(ticket, ticketResponsedto);
		ticketResponsedto.setBookingDate(ticket.getBookingDate());
		
		return  ticketResponsedto;
	}
}
