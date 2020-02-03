package com.easyfly.booking.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * @author PriyaDharshini S.
 * @since 2020-02-03. This controller will get all the locations.
 * @return List<Location> which has list of location.
 * @throws LocationNotFoundException it will throw the exception if the location
 *                                   is not there.
 * 
 */
@RestController
@RequestMapping("/locations")
@CrossOrigin
public class LocationController {

	@Autowired
	LocationService locationService;

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-03. This method will get all the locations.
	 * @return List<Location> which has list of location.
	 * @throws LocationNotFoundException it will throw the exception if the location
	 *                                   is not there.
	 * 
	 */
	@GetMapping
	public ResponseEntity<List<Location>> getAllLocations() throws LocationNotFoundException {
		logger.info("Entering into LocationController: getting all locations");
		return new ResponseEntity<>(locationService.getAllLocations(), HttpStatus.OK);
	}

}
