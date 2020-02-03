package com.easyfly.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyfly.booking.constant.Constant;
import com.easyfly.booking.dto.TicketRequestDto;
import com.easyfly.booking.dto.TicketResponsedto;
import com.easyfly.booking.exception.FlightNotFoundException;
import com.easyfly.booking.service.TicketService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
public class TicketController {

	@Autowired
	TicketService ticketService;
	
	@PostMapping
	public ResponseEntity<TicketResponsedto> reserveTicket(@RequestBody TicketRequestDto ticketRequestDto) throws FlightNotFoundException{	
		log.info("Entering into reserveTicket of TicketController");
		TicketResponsedto ticketResponsedto= ticketService.reserveTicket(ticketRequestDto);
		ticketResponsedto.setMessage(Constant.SUCCESS_MESSAGE);
		ticketResponsedto.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(ticketResponsedto, HttpStatus.OK);		
	}
}
