package com.easyfly.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyfly.booking.constant.Constant;
import com.easyfly.booking.entity.Location;
import com.easyfly.booking.exception.LocationNotFoundException;
import com.easyfly.booking.repository.LocationRepository;

/**
 * @author PriyaDharshini S.
 * @since 2020-02-03. This service will get all the locations.
 * 
 */
@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	LocationRepository locationRepository;

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-03. This method will get all the locations.
	 * @return List<Location> which has list of location.
	 * @throws LocationNotFoundException it will throw the exception if the location
	 *                                   is not there.
	 * 
	 */
	@Override
	public List<Location> getAllLocations() throws LocationNotFoundException {
		List<Location> locations = locationRepository.findAll();
		if (locations.isEmpty()) {
			throw new LocationNotFoundException(Constant.LOCATION_NOT_FOUND);
		} else {
			return locations;
		}
	}

}
