package com.easyfly.booking.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyfly.booking.constant.Constant;
import com.easyfly.booking.dto.ResponseDto;
import com.easyfly.booking.dto.TicketDetailsResponseDto;
import com.easyfly.booking.exception.CancelTicketBeforeRangeException;
import com.easyfly.booking.exception.PassengerNotFoundException;
import com.easyfly.booking.exception.TicketNotFoundException;
import com.easyfly.booking.service.TicketService;

@RestController
@RequestMapping("/tickets")
@CrossOrigin
public class TicketController {

	@Autowired
	TicketService ticketService;

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

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
		logger.info("Entering into TicketController: getting ticket details");
		return new ResponseEntity<>(ticketService.getTicketDetails(ticketId), HttpStatus.OK);
	}

	@DeleteMapping("/{ticketId}")
	public ResponseEntity<ResponseDto> cancelBooking(@PathVariable Long ticketId)
			throws TicketNotFoundException, PassengerNotFoundException, CancelTicketBeforeRangeException {
		ResponseDto responseDto = new ResponseDto();
		ticketService.cancleBooking(ticketId);
		responseDto.setStatusCode(HttpStatus.OK.value());
		responseDto.setMessage(Constant.TICKET_CANCELLED_SUCCESSFULLY);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

}
