package com.easyfly.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfly.booking.entity.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer>{

}
