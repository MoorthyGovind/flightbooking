package com.easyfly.booking.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.easyfly.booking.entity.Location;
import com.easyfly.booking.exception.LocationNotFoundException;
import com.easyfly.booking.repository.LocationRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LocationServiceTest {

	@InjectMocks
	LocationServiceImpl locationServiceImpl;

	@Mock
	LocationRepository locationRepository;

	Location location = new Location();
	List<Location> locations = new ArrayList<>();

	@Before
	public void setUp() {
		location.setLocationId(1);
		location.setLocationName("trichy");
		locations.add(location);
	}

	@Test
	public void testGetAllLocation() throws LocationNotFoundException {
		Mockito.when(locationRepository.findAll()).thenReturn(locations);
		List<Location> actual = locationServiceImpl.getAllLocations();
		assertEquals(1, actual.size());
	}

	@Test(expected = LocationNotFoundException.class)
	public void testGetAllLocationForLocationNotFoundException() throws LocationNotFoundException {
		locations = new ArrayList<>();
		Mockito.when(locationRepository.findAll()).thenReturn(locations);
		locationServiceImpl.getAllLocations();

	}

}
