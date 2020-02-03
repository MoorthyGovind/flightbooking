package com.easyfly.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.easyfly.booking.constant.Constant;
import com.easyfly.booking.dto.ErrorDto;


@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(FlightNotFoundException.class)
	public ResponseEntity<ErrorDto> flightNotFoundException(){
		ErrorDto errorDto= new ErrorDto();
		errorDto.setErrorStatusMessage(Constant.FAILURE_MESSAGE);
		errorDto.setErrorStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}
}
