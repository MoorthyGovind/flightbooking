package com.easyfly.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyfly.booking.constant.Constant;
import com.easyfly.booking.dto.PassengerDto;
import com.easyfly.booking.dto.TicketDetailsResponseDto;
import com.easyfly.booking.entity.Passenger;
import com.easyfly.booking.entity.Ticket;
import com.easyfly.booking.exception.PassengerNotFoundException;
import com.easyfly.booking.exception.TicketNotFoundException;
import com.easyfly.booking.repository.PassengerRepository;
import com.easyfly.booking.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	PassengerRepository passengerRepository;

	@Override
	public TicketDetailsResponseDto getTicketDetails(Long ticketId)
			throws TicketNotFoundException, PassengerNotFoundException {
		Optional<Ticket> ticket = ticketRepository.findById(ticketId);
		if (!ticket.isPresent()) {
			throw new TicketNotFoundException(Constant.TICKET_NOT_FOUND);
		} else {
			List<Passenger> passengers = passengerRepository.findAllByTicketId(ticket.get());
			if (passengers.isEmpty()) {
				throw new PassengerNotFoundException(Constant.PASSENGER_NOT_FOUND);
			} else {
				TicketDetailsResponseDto ticketDetailsResponseDto = new TicketDetailsResponseDto();
				BeanUtils.copyProperties(ticket.get(), ticketDetailsResponseDto);
				ticketDetailsResponseDto.setArrivalTime(ticket.get().getFlightScheduleId().getArrivalTime());
				ticketDetailsResponseDto.setDepartureTime(ticket.get().getFlightScheduleId().getDepartureTime());
				ticketDetailsResponseDto
						.setSource(ticket.get().getFlightScheduleId().getFlightId().getSourceId().getLocationName());
				ticketDetailsResponseDto.setDestination(
						ticket.get().getFlightScheduleId().getFlightId().getDestinationId().getLocationName());
				List<PassengerDto> passengerList = new ArrayList<>();
				passengers.forEach(passengerDetails -> {
					PassengerDto passengerDto = new PassengerDto();
					BeanUtils.copyProperties(passengerDetails, passengerDto);
					passengerList.add(passengerDto);
				});
				ticketDetailsResponseDto.setPassengers(passengerList);
				return ticketDetailsResponseDto;

			}

		}
	}
}
