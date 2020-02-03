package com.easyfly.booking.service;

import java.util.List;

import com.easyfly.booking.entity.Location;
import com.easyfly.booking.exception.LocationNotFoundException;

public interface LocationService {
	
	List<Location> getAllLocations() throws LocationNotFoundException;

}
