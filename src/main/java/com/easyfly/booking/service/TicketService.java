package com.easyfly.booking.service;

import com.easyfly.booking.dto.TicketDetailsResponseDto;
import com.easyfly.booking.exception.PassengerNotFoundException;
import com.easyfly.booking.exception.TicketNotFoundException;

public interface TicketService {
	
	TicketDetailsResponseDto getTicketDetails(Long ticketId) throws TicketNotFoundException, PassengerNotFoundException;

}
