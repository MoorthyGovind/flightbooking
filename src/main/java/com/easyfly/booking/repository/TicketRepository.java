package com.easyfly.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfly.booking.entity.FlightSchedule;
import com.easyfly.booking.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	Ticket findByFlightScheduleId(FlightSchedule flightSchedule);

}
