package com.easyfly.booking.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.easyfly.booking.entity.Location;
import com.easyfly.booking.exception.LocationNotFoundException;
import com.easyfly.booking.service.LocationService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LocationControllerTest {
	
	@InjectMocks
	LocationController locationController;

	@Mock
	LocationService locationService;

	Location location = new Location();
	List<Location> locations = new ArrayList<>();

	@Before
	public void setUp() {
		location.setLocationId(1);
		location.setLocationName("trichy");
		locations.add(location);
	}

	@Test
	public void testGetAllLocations() throws LocationNotFoundException {
		
		Mockito.when(locationService.getAllLocations()).thenReturn(locations);
		ResponseEntity<List<Location>> actual = locationController.getAllLocations();
		assertEquals(HttpStatus.OK, actual.getStatusCode());
		
	}

}
