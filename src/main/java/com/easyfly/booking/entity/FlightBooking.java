package com.easyfly.booking.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@SequenceGenerator(name = "bookingId", initialValue = 5000, allocationSize = 1)
@Entity
public class FlightBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookingId")
	private Integer bookingId;
	private LocalDate bookingDate;
	@ManyToOne
	@JoinColumn(name = "flight_schedule_id")
	private FlightSchedule flightScheduleId;
	private String emailId;
	private Long phoneNumber;
	private String paymentType;
	private Double totalFare;

}
