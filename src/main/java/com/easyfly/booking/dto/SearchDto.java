package com.easyfly.booking.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.easyfly.booking.constant.Constant;

import lombok.Getter;
import lombok.Setter;

/**
 * SearchDto - Request details model for the search flight.
 * 
 * @author Goivndasamy.C
 * @since 04-02-2020
 * @version V1.1
 *
 */
@Getter
@Setter
public class SearchDto {

	@NotNull(message = Constant.SOURCE_EMPTY)
	private String source;

	@NotNull(message = Constant.DESTINATION_EMPTY)
	private String destination;

	@DateTimeFormat(pattern = Constant.DATE_TIME_FORMAT_PATTERN)
	private String date;

	@Min(value = 1, message = Constant.NO_OF_PASSENGERS_LESS_THAN_1)
	private Integer noOfPassengers;

	@NotNull(message = Constant.TRAVEL_TYPE_EMPTY)
	private String travelType;

}
