package com.easyfly.booking.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.easyfly.booking.common.BookingEnum.PaymentType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRequestDto{

    @NotNull(message="flightScheduleId cannot be null")
	private Integer flightScheduleId;
    
    @NotEmpty @Email(message = "Invalid Email")
	private String emailId;
    
    @NotNull @Min(6) @Max(10)
	private Long phoneNumber;
    
    @Min(value = 1, message = "noOfPassengers can't be less than 1")
	private Integer noOfPassengers;
    
    @NotNull
	private PaymentType paymentType;
	private double totalFare;
	
	@NotEmpty
	private List<PassengerDto> passagerList;
}
