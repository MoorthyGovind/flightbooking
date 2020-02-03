package com.easyfly.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyfly.booking.entity.Location;
import com.easyfly.booking.exception.LocationNotFoundException;
import com.easyfly.booking.service.LocationService;

@RestController
@RequestMapping("/locations")
@CrossOrigin
public class LocationController {

	@Autowired
	LocationService locationService;

	@GetMapping
	public ResponseEntity<List<Location>> getAllLocations() throws LocationNotFoundException {
		return new ResponseEntity<>(locationService.getAllLocations(), HttpStatus.OK);
	}

}
