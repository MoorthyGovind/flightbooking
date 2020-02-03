package com.easyfly.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfly.booking.entity.Flight;
@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer>{

}
