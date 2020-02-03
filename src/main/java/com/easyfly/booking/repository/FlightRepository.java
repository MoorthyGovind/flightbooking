package com.easyfly.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfly.booking.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

	List<Flight> findBySourceIdLocationIdAndDestinationIdLocationId(Integer sourceId, Integer destinationId);

}
