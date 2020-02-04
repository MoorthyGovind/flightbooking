package com.easyfly.booking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.easyfly.booking.common.BookingEnum;
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

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceImplTest {

	@InjectMocks
	FlightServiceImpl flightServiceImpl;

	@Mock
	LocationRepository locationRepository;

	@Mock
	FlightRepository flightRepository;

	@Mock
	FlightScheduleRepository flightScheduleRepository;

	Location location = new Location();
	Location destinationLocation = new Location();

	Flight flight = new Flight();
	FlightSchedule flightSchedule = new FlightSchedule();

	SearchDto searchDto = new SearchDto();

	@Before
	public void init() {
		searchDto.setSource("Coimbatore");
		searchDto.setDestination("Chennai");
		searchDto.setTravelType("Business");
		searchDto.setNoOfPassengers(1);
		searchDto.setDate("2020-04-01");

		location.setLocationId(1);
		destinationLocation.setLocationId(2);

		flight.setFlightId(1);
		flight.setSourceId(location);
		flight.setDestinationId(destinationLocation);

		flightSchedule.setFlightId(flight);
		flightSchedule.setFlightScheduleId(1);
	}

	@Test
	public void testGetAvailableFlights() throws InvalidLocationException, SameLocationException {
		List<Flight> flights = new ArrayList<>();
		flights.add(flight);

		when(locationRepository.findByLocationName(searchDto.getSource())).thenReturn(Optional.of(location));
		when(locationRepository.findByLocationName(searchDto.getDestination()))
				.thenReturn(Optional.of(destinationLocation));
		when(flightRepository.findBySourceIdLocationIdAndDestinationIdLocationId(1, 2)).thenReturn(flights);
		when(flightScheduleRepository
				.findByFlightIdAndTravelTypeIdAndFlightScheduledDateAndAvailableSeatsGreaterThanEqual(flight,
						BookingEnum.TravelType.BUSINESS.getTravelType(), LocalDate.of(2020, 4, 01),
						searchDto.getNoOfPassengers())).thenReturn(Optional.of(flightSchedule));

		SearchResponseDto searchResponseDto = flightServiceImpl.getAvailableFlights(searchDto);
		assertThat(searchResponseDto.getFlightList()).hasSize(1);
	}

	@Test(expected = InvalidLocationException.class)
	public void testGetAvailableFlightsForInvalidLocationException()
			throws InvalidLocationException, SameLocationException {
		when(locationRepository.findByLocationName(searchDto.getSource())).thenReturn(Optional.ofNullable(null));
		flightServiceImpl.getAvailableFlights(searchDto);
	}

	@Test(expected = InvalidLocationException.class)
	public void testGetAvailableFlightsForInvalidDestLocationException()
			throws InvalidLocationException, SameLocationException {
		when(locationRepository.findByLocationName(searchDto.getSource())).thenReturn(Optional.of(location));
		when(locationRepository.findByLocationName(searchDto.getDestination())).thenReturn(Optional.ofNullable(null));
		flightServiceImpl.getAvailableFlights(searchDto);
	}

	@Test(expected = SameLocationException.class)
	public void testGetAvailableFlightsForSameLocationException()
			throws InvalidLocationException, SameLocationException {
		searchDto.setDestination("Coimbatore");
		when(locationRepository.findByLocationName(searchDto.getSource())).thenReturn(Optional.of(location));
		when(locationRepository.findByLocationName(searchDto.getDestination()))
				.thenReturn(Optional.of(location));
		flightServiceImpl.getAvailableFlights(searchDto);
	}

}
