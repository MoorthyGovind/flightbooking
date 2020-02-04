package com.easyfly.booking.controller;

import javax.naming.NamingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyfly.booking.constant.Constant;
import com.easyfly.booking.dto.ResponseDto;
import com.easyfly.booking.dto.TicketDetailsResponseDto;
import com.easyfly.booking.dto.TicketRequestDto;
import com.easyfly.booking.dto.TicketResponsedto;
import com.easyfly.booking.exception.CancelTicketBeforeRangeException;
import com.easyfly.booking.exception.FlightNotFoundException;
import com.easyfly.booking.exception.PassengerNotFoundException;
import com.easyfly.booking.exception.TicketNotFoundException;
import com.easyfly.booking.service.TicketService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
public class TicketController {
	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

	@Autowired
	TicketService ticketService;

	/**
	 * This method is used to reserveTicket for one journey flight ticket
	 * 
	 * @author Chethana M
	 * @param ticketRequestDto - Takes parameters which are required to reserve
	 *                         ticket
	 * @throws FlightNotFoundException - Thrown when flight is not found
	 * @throws NamingException         - Thrown when Payment Exception occurs
	 * @return TicketResponsedto - Returns Booking details
	 * 
	 */
	@PostMapping
	public ResponseEntity<TicketResponsedto> reserveTicket(@Valid @RequestBody TicketRequestDto ticketRequestDto)
			throws FlightNotFoundException, NamingException {
		log.info("Entering into reserveTicket of TicketController");
		TicketResponsedto ticketResponsedto = ticketService.reserveTicket(ticketRequestDto);
		ticketResponsedto.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(ticketResponsedto, HttpStatus.OK);
	}

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-03. This method will get particular ticket details by passing
	 *        the ticketId.
	 * @param ticketId.This is the id of the ticket.
	 * @return TicketDetailsResponseDto which has ticketDetails.
	 * @throws TicketNotFoundException    it will throw the exception if the ticket
	 *                                    is not there.
	 * @throws PassengerNotFoundException it will throw the exception if the
	 *                                    passenger is not there.
	 * 
	 */
	@GetMapping("/{ticketId}")
	public ResponseEntity<TicketDetailsResponseDto> getTicketDetails(@PathVariable Long ticketId)
			throws TicketNotFoundException, PassengerNotFoundException {
		if (ticketId == null) {
			throw new TicketNotFoundException(Constant.TICKET_NOT_FOUND);
		} else {
			logger.info("Entering into TicketController: getting ticket details");
			return new ResponseEntity<>(ticketService.getTicketDetails(ticketId), HttpStatus.OK);
		}
	}

	@DeleteMapping("/{ticketId}")
	public ResponseEntity<ResponseDto> cancelTicket(@PathVariable Long ticketId)
			throws TicketNotFoundException, PassengerNotFoundException, CancelTicketBeforeRangeException {
		if (ticketId == null) {
			throw new TicketNotFoundException(Constant.TICKET_NOT_FOUND);
		} else {
			ResponseDto responseDto = new ResponseDto();
			ticketService.cancelTicket(ticketId);
			responseDto.setStatusCode(HttpStatus.OK.value());
			responseDto.setMessage(Constant.TICKET_CANCELLED_SUCCESSFULLY);
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
		}
	}
	
	

}
