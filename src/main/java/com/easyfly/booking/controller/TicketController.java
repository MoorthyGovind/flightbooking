package com.easyfly.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyfly.booking.dto.TicketDetailsResponseDto;
import com.easyfly.booking.exception.PassengerNotFoundException;
import com.easyfly.booking.exception.TicketNotFoundException;
import com.easyfly.booking.service.TicketService;

@RestController
@RequestMapping("/tickets")
@CrossOrigin
public class TicketController {
	
	@Autowired
	TicketService ticketService;
	
	@GetMapping("/{ticketId}")
	public ResponseEntity<TicketDetailsResponseDto> getTicketDetails(@PathVariable Long ticketId) throws TicketNotFoundException, PassengerNotFoundException{
			return new ResponseEntity<>(ticketService.getTicketDetails(ticketId),HttpStatus.OK);
	}

}
