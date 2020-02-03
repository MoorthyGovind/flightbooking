package com.easyfly.booking.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyfly.booking.common.BookingEnum;
import com.easyfly.booking.common.BookingEnum.TravelType;
import com.easyfly.booking.constant.Constant;
import com.easyfly.booking.converter.FlightConverter;
import com.easyfly.booking.dto.FlightDto;
import com.easyfly.booking.dto.SearchDto;
import com.easyfly.booking.dto.SearchResponseDto;
import com.easyfly.booking.entity.Flight;
import com.easyfly.booking.entity.FlightSchedule;
import com.easyfly.booking.entity.Location;
import com.easyfly.booking.exception.InvalidLocationException;
import com.easyfly.booking.exception.SameLocationException;
import com.easyfly.booking.repository.FlightRepository;
import com.easyfly.booking.repository.FlightScheduleRepository;
import com.easyfly.booking.repository.LocationRepository;

@Service
public class FlightServiceImpl implements FlightService {
	private static final Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	FlightRepository flightRepository;

	@Autowired
	FlightScheduleRepository flightScheduleRepository;

	@Override
	public SearchResponseDto getAvailableFlights(SearchDto searchDto)
			throws InvalidLocationException, SameLocationException {
		logger.info("get the available flights based on the search params.");

		SearchResponseDto searchResponseDto = new SearchResponseDto();
		// Check Source Location is Valid or not.
		Optional<Location> sourceLocation = locationRepository.findByLocationName(searchDto.getSource());
		if (!sourceLocation.isPresent()) {
			logger.error("Error Occured while finding a source location.");
			throw new InvalidLocationException(Constant.INVALID_SOURCE_LOCATION);
		}

		// Check Destination Location is Valid or not.
		Optional<Location> destinationLocation = locationRepository.findByLocationName(searchDto.getDestination());
		if (!destinationLocation.isPresent()) {
			logger.error("Error Occured while finding a destination location.");
			throw new InvalidLocationException(Constant.INVALID_DESTINATION_LOCATION);
		}

		// Check Source and Destination location is same or not
		if (searchDto.getSource().equalsIgnoreCase(searchDto.getDestination())) {
			logger.error("Error Occured - Source and Destination location is same.");
			throw new SameLocationException(Constant.SOURCE_DESTINATION_SHOULD_NOT_SAME);
		}

		TravelType travelType = BookingEnum.TravelType.valueOf(searchDto.getTravelType().toUpperCase());
		LocalDate scheduleDate = LocalDate.parse(searchDto.getDate());

		List<FlightDto> flightDtos = new ArrayList<>();
		// Find the flights based on the source and destination
		List<Flight> flights = flightRepository.findBySourceIdLocationIdAndDestinationIdLocationId(
				sourceLocation.get().getLocationId(), destinationLocation.get().getLocationId());
		flights.forEach(flight -> {
			Optional<FlightSchedule> flightSchedule = flightScheduleRepository
					.findByFlightIdAndTravelTypeIdAndFlightScheduledDate(flight, travelType.getTravelType(),
							scheduleDate);
			if (flightSchedule.isPresent()) {
				flightDtos.add(FlightConverter.convertDto(flight, flightSchedule.get()));
			}
		});
		searchResponseDto.setFlightList(flightDtos);
		return searchResponseDto;
	}
}
