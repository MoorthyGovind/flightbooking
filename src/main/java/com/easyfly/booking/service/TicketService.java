package com.easyfly.booking.service;

import com.easyfly.booking.dto.TicketDetailsResponseDto;
import com.easyfly.booking.exception.CancelTicketBeforeRangeException;
import com.easyfly.booking.exception.PassengerNotFoundException;
import com.easyfly.booking.exception.TicketNotFoundException;

public interface TicketService {

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
	TicketDetailsResponseDto getTicketDetails(Long ticketId) throws TicketNotFoundException, PassengerNotFoundException;

	public void cancleBooking(Long ticketId)
			throws TicketNotFoundException, PassengerNotFoundException, CancelTicketBeforeRangeException;
}
