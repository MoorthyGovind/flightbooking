package com.easyfly.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfly.booking.entity.FlightSchedule;
@Repository
public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Integer>{

}
