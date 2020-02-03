package com.easyfly.booking.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyfly.booking.constant.Constant;
import com.easyfly.booking.dto.PassengerDto;
import com.easyfly.booking.dto.TicketDetailsResponseDto;
import com.easyfly.booking.entity.FlightSchedule;
import com.easyfly.booking.entity.Passenger;
import com.easyfly.booking.entity.Ticket;
import com.easyfly.booking.exception.CancelTicketBeforeRangeException;
import com.easyfly.booking.exception.PassengerNotFoundException;
import com.easyfly.booking.exception.TicketNotFoundException;
import com.easyfly.booking.repository.FlightScheduleRepository;
import com.easyfly.booking.repository.PassengerRepository;
import com.easyfly.booking.repository.TicketRepository;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	PassengerRepository passengerRepository;
	
	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

	@Autowired
	FlightScheduleRepository flightScheduleRepository;

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
	@Override
	public TicketDetailsResponseDto getTicketDetails(Long ticketId)
			throws TicketNotFoundException, PassengerNotFoundException {
		Optional<Ticket> ticket = ticketRepository.findById(ticketId);
		if (!ticket.isPresent()) {
			logger.error("Entering into TicketServiceImpl:"+Constant.TICKET_NOT_FOUND);
			throw new TicketNotFoundException(Constant.TICKET_NOT_FOUND);
		} else {
			List<Passenger> passengers = passengerRepository.findAllByTicketId(ticket.get());
			if (passengers.isEmpty()) {
				logger.error("Entering into TicketServiceImpl:"+Constant.PASSENGER_NOT_FOUND);
				throw new PassengerNotFoundException(Constant.PASSENGER_NOT_FOUND);
			} else {
				logger.info("Entering into TicketServiceImpl:getting ticket details");
				TicketDetailsResponseDto ticketDetailsResponseDto = new TicketDetailsResponseDto();
				BeanUtils.copyProperties(ticket.get(), ticketDetailsResponseDto);
				ticketDetailsResponseDto.setFlightName(ticket.get().getFlightScheduleId().getFlightId().getFlightName());
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

	@Override
	public void cancleBooking(Long ticketId)
			throws TicketNotFoundException, PassengerNotFoundException, CancelTicketBeforeRangeException {
		Optional<Ticket> ticketDetail = ticketRepository.findById(ticketId);
		if (!ticketDetail.isPresent()) {
			throw new TicketNotFoundException(Constant.TICKET_NOT_FOUND);
		}

		List<Passenger> passengers = passengerRepository.findAllByTicketId(ticketDetail.get());
		if (passengers.isEmpty()) {
			throw new PassengerNotFoundException(Constant.PASSENGER_NOT_FOUND);
		}

		Optional<FlightSchedule> flightSchedule = flightScheduleRepository
				.findById(ticketDetail.get().getFlightScheduleId().getFlightScheduleId());
		FlightSchedule updateFlightSchedule = flightSchedule.get();

		// Check cancel for before one day validation.
		LocalDate currentDate = LocalDate.now();

		Boolean isBefore = currentDate.isBefore(flightSchedule.get().getFlightScheduledDate());
		if (!isBefore) {
			throw new CancelTicketBeforeRangeException(Constant.TICKET_CANCELLED_BEFORE_RANGE);
		}

		Integer availableSeatsUpdate = flightSchedule.get().getAvailableSeats() + passengers.size();

		updateFlightSchedule.setAvailableSeats(availableSeatsUpdate);
		flightScheduleRepository.save(updateFlightSchedule);

		//Update ticket booking status as "Canceled"
		Ticket statusUpdate = ticketDetail.get();
		statusUpdate.setStatus(Constant.TICKET_BOOKING_CANCELLED);
		ticketRepository.save(statusUpdate);
	}
}
