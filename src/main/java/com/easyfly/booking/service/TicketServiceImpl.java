package com.easyfly.booking.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyfly.booking.constant.Constant;
import com.easyfly.booking.dto.PassengerDto;
import com.easyfly.booking.dto.TicketDetailsResponseDto;
import com.easyfly.booking.dto.TicketRequestDto;
import com.easyfly.booking.dto.TicketResponsedto;
import com.easyfly.booking.entity.FlightSchedule;
import com.easyfly.booking.entity.Passenger;
import com.easyfly.booking.entity.Ticket;
import com.easyfly.booking.exception.CancelTicketBeforeRangeException;
import com.easyfly.booking.exception.FlightNotFoundException;
import com.easyfly.booking.exception.PassengerNotFoundException;
import com.easyfly.booking.exception.TicketNotFoundException;
import com.easyfly.booking.repository.FlightScheduleRepository;
import com.easyfly.booking.repository.PassengerRepository;
import com.easyfly.booking.repository.TicketRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to perform the flight ticket related operation like
 * booking, ticket cancellation,track ticket details
 * 
 * @author Chethana
 * @since 04-02-2020
 * @version V1.1
 *
 */
@Service
@Slf4j
@Transactional
public class TicketServiceImpl implements TicketService {
	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	PassengerRepository passengerRepository;

	@Autowired
	FlightScheduleRepository flightScheduleRepository;

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
	public TicketResponsedto reserveTicket(TicketRequestDto ticketRequestDto)
			throws FlightNotFoundException, NamingException {
		log.info("Entering into reserveTicket of TicketServiceImpl");

		Optional<FlightSchedule> flightSchedule = flightScheduleRepository
				.findById(ticketRequestDto.getFlightScheduleId());
		if (!flightSchedule.isPresent()) {
			log.error("Exception Occured in reserveTicket of TicketServiceImpl:" + Constant.FLIGHT_NOT_FOUND);
			throw new FlightNotFoundException(Constant.FLIGHT_NOT_FOUND);
		}
		if (flightSchedule.get().getAvailableSeats() < ticketRequestDto.getNoOfPassengers()) {
			log.error("Exception Occured in reserveTicket of TicketServiceImpl:" + Constant.INSUFFICIENT_TICKETS);
			throw new FlightNotFoundException(Constant.INSUFFICIENT_TICKETS);
		}
		Ticket ticket = new Ticket();
		flightSchedule.get()
				.setAvailableSeats(flightSchedule.get().getAvailableSeats() - ticketRequestDto.getNoOfPassengers());
		BeanUtils.copyProperties(ticketRequestDto, ticket);
		ticket.setStatus(Constant.STATUS_BOOKED);
		ticket.setTotalFare(ticketRequestDto.getTotalFare());
		ticket.setFlightScheduleId(flightSchedule.get());
		ticket.setPaymentType(ticketRequestDto.getPaymentType());
		ticket.setBookingDate(LocalDate.now());
		ticket = ticketRepository.save(ticket);
		PaymentService paymentService = ServiceLocator.getService(ticketRequestDto.getPaymentType().toString());
		String message = paymentService.execute();
		final Ticket ticketDetails = ticket;
		paymentService.execute();

		List<PassengerDto> passengerList = ticketRequestDto.getPassagerList();
		passengerList.forEach(passengerIndex -> {
			Passenger passenger = new Passenger();
			BeanUtils.copyProperties(passengerIndex, passenger);
			passenger.setTicketId(ticketDetails);
			passenger.setAadharNumber(passengerIndex.getAadharNumber());
			passengerRepository.save(passenger);
		});

		log.debug("Entering into reserveTicket of TicketServiceImpl:Ticket booked successfully");
		TicketResponsedto ticketResponsedto = new TicketResponsedto();
		BeanUtils.copyProperties(ticket, ticketResponsedto);
		ticketResponsedto.setBookingDate(ticket.getBookingDate());
		ticketResponsedto.setMessage(message);
		return ticketResponsedto;
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
	@Override
	public TicketDetailsResponseDto getTicketDetails(Long ticketId)
			throws TicketNotFoundException, PassengerNotFoundException {
		Optional<Ticket> ticket = ticketRepository.findById(ticketId);
		if (!ticket.isPresent()) {
			log.error("Entering into TicketServiceImpl:" + Constant.TICKET_NOT_FOUND);
			throw new TicketNotFoundException(Constant.TICKET_NOT_FOUND);
		} else {
			List<Passenger> passengers = passengerRepository.findAllByTicketId(ticket.get());
			if (passengers.isEmpty()) {
				log.error("Entering into TicketServiceImpl:" + Constant.PASSENGER_NOT_FOUND);
				throw new PassengerNotFoundException(Constant.PASSENGER_NOT_FOUND);
			} else {
				log.info("Entering into TicketServiceImpl:getting ticket details");
				TicketDetailsResponseDto ticketDetailsResponseDto = new TicketDetailsResponseDto();
				BeanUtils.copyProperties(ticket.get(), ticketDetailsResponseDto);
				ticketDetailsResponseDto
						.setFlightName(ticket.get().getFlightScheduleId().getFlightId().getFlightName());
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

	/**
	 * Cancel the ticket the based on the ticketId
	 * 
	 * @param ticketId - Id of the ticket.
	 * @return details with status code and message as a responseDto.
	 * @throws TicketNotFoundException          - Throws the TicketNotFoundException
	 *                                          when ticket details not found.
	 * @throws PassengerNotFoundException       - Throws the
	 *                                          PassengerNotFoundException when
	 *                                          passenger not found
	 * @throws CancelTicketBeforeRangeException - Throws the
	 *                                          CancelTicketBeforeRangeException
	 *                                          when the range as before of the
	 *                                          current date while canceling the
	 *                                          ticket.
	 */
	@Override
	public void cancelTicket(Long ticketId)
			throws TicketNotFoundException, PassengerNotFoundException, CancelTicketBeforeRangeException {
		Optional<Ticket> ticketDetail = ticketRepository.findById(ticketId);
		if (!ticketDetail.isPresent()) {
			throw new TicketNotFoundException(Constant.TICKET_NOT_FOUND);
		}

		List<Passenger> passengers = passengerRepository.findAllByTicketId(ticketDetail.get());
		if (passengers.isEmpty()) {
			throw new PassengerNotFoundException(Constant.PASSENGER_NOT_FOUND);
		}

		FlightSchedule flightSchedule = flightScheduleRepository
				.findByFlightScheduleId(ticketDetail.get().getFlightScheduleId().getFlightScheduleId());

		// Check cancel for before one day validation.
		LocalDate currentDate = LocalDate.now();
		Boolean isBefore = currentDate.isBefore(flightSchedule.getFlightScheduledDate());
		if (!isBefore) {
			throw new CancelTicketBeforeRangeException(Constant.TICKET_CANCELLED_BEFORE_RANGE);
		}

		Integer availableSeatsUpdate = flightSchedule.getAvailableSeats() + passengers.size();

		flightSchedule.setAvailableSeats(availableSeatsUpdate);
		flightScheduleRepository.save(flightSchedule);

		// Update ticket booking status as "Canceled"
		Ticket statusUpdate = ticketDetail.get();
		statusUpdate.setStatus(Constant.TICKET_BOOKING_CANCELLED);
		ticketRepository.save(statusUpdate);
	}
}
