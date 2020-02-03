package com.easyfly.booking.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfly.booking.entity.Flight;
import com.easyfly.booking.entity.FlightSchedule;

@Repository
public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Integer> {

	Optional<FlightSchedule> findByFlightIdAndTravelTypeIdAndFlightScheduledDateAndAvailableSeatsGreaterThanEqual(Flight flight,
			Integer travelTypeId, LocalDate scheduleDate, Integer noOfPassengers);
}
