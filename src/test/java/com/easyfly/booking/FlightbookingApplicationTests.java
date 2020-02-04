package com.easyfly.booking;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlightbookingApplicationTests {

	@Test
	public void applicationTest() {
		FlightbookingApplication.main(new String[] {});
		assertTrue(true);
	}
}
