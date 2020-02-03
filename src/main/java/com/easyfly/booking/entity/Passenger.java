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
public class Passenger {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer passengerId;
	@ManyToOne
	@JoinColumn(name = "ticket_id")
	private Ticket ticketId;
	private String name;
	private Integer age;
	private Long aadharNumber;
}
