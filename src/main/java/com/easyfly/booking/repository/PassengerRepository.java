package com.easyfly.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easyfly.booking.entity.Passenger;
import com.easyfly.booking.entity.Ticket;
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer>{

	List<Passenger> findAllByTicketId(Ticket ticketId);

}
