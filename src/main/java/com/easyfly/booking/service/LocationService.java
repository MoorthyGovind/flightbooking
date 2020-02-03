package com.easyfly.booking.service;

import java.util.List;

import com.easyfly.booking.entity.Location;
import com.easyfly.booking.exception.LocationNotFoundException;
/**
 * @author PriyaDharshini S.
 * @since 2020-02-03. This service will get all the locations.
 * 
 */
public interface LocationService {
	
	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-03. This method will get all the locations.
	 * @return List<Location> which has list of location.
	 * @throws LocationNotFoundException it will throw the exception if the location
	 *                                   is not there.
	 * 
	 */
	List<Location> getAllLocations() throws LocationNotFoundException;

}
