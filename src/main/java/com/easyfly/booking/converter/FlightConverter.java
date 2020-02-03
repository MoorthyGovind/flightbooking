package com.easyfly.booking.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easyfly.booking.dto.FlightDto;
import com.easyfly.booking.entity.Flight;
import com.easyfly.booking.entity.FlightSchedule;

/**
 * FlightConverter class, Convert the entity flight and schedule details to
 * flightDto model.
 * 
 * @author Govindasamy.C
 * @since 03-02-2020
 *
 */
public class FlightConverter {
	private static final Logger logger = LoggerFactory.getLogger(FlightConverter.class);

	private FlightConverter() {
	}

	/**
	 * Convert the entity flight and schedule details to flightDto model.
	 * 
	 * @param flight         deatils of the flight.
	 * @param flightSchedule details of the flight schedule
	 * @return set the values to flightDto object from flight and flightSchedule
	 * @author Govindasamy.C
	 * @since 03-02-2020
	 */
	public static FlightDto convertDto(Flight flight, FlightSchedule flightSchedule) {
		logger.info("Convert the entity flight and schedule details to flightDto model.");
		FlightDto flightDto = new FlightDto();
		flightDto.setFlightId(flight.getFlightId());
		flightDto.setFlightName(flight.getFlightName());
		flightDto.setFare(flightSchedule.getFare());
		flightDto.setFlightScheduleId(flightSchedule.getFlightScheduleId());
		flightDto.setScheduleDate(flightSchedule.getFlightScheduledDate());
		flightDto.setAvailableSeats(flightSchedule.getAvailableSeats());
		flightDto.setDestination(flight.getDestinationId().getLocationName());
		flightDto.setSource(flight.getSourceId().getLocationName());
		flightDto.setArrivalTime(flightSchedule.getArrivalTime());
		flightDto.setDepartureTime(flightSchedule.getDepartureTime());
		return flightDto;
	}
}
