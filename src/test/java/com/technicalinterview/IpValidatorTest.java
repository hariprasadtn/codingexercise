package com.technicalinterview;

import static org.junit.Assert.assertEquals;
import org.junit.Test;



public class IpValidatorTest {
	
	@Test
	public void testIpRange(){
		assertEquals("Test",IpValidator.validateIpAddress("192.168.0.25", "192.168.0.21/24"), true);
		assertEquals("Test",IpValidator.validateIpAddress("129.141.189.32", "192.168.0.21/24"), false);
	}

}
