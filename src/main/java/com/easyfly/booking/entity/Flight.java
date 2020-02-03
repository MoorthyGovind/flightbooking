package com.easyfly.booking.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer flightId;
	private String flightName;
	@ManyToOne
	@JoinColumn(name = "source_id")
	private Location sourceId; 
	@ManyToOne
	@JoinColumn(name = "destination_id")
	private Location destinationId; 
	private Integer noOfSeats;
}
