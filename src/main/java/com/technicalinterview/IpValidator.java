package com.technicalinterview;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpValidator {

	private static final Pattern addressPattern = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
	private static final Pattern cidrPattern = Pattern
			.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})/(\\d{1,2})");

	/**
	 * To validate if the IPaddress in the CIDR range
	 * 
	 * @param ipAddress
	 * @param cidrRange
	 * @return
	 */
	
	public static boolean validateIpAddress(String ipAddress, String cidrRange) {

		if (null == ipAddress || null == cidrRange)
			throw new IllegalArgumentException("Not a valid Input");

		Matcher addressMatcher = addressPattern.matcher(ipAddress);
		if (!addressMatcher.matches())
			throw new IllegalArgumentException(ipAddress + " is not a valid IP address");
		if (!isValidIp(ipAddress))
			throw new IllegalArgumentException(ipAddress + " is not a valid IP address");

		Matcher cidrMatcher = cidrPattern.matcher(cidrRange);
		if (!cidrMatcher.matches()) {
			if (!cidrRange.contains("/") && addressPattern.matcher(cidrRange).matches())
				cidrRange = new StringBuilder(cidrRange).append("/32").toString();
			else
				throw new IllegalArgumentException(cidrRange + " is not a valid CIDR notation");
		}
		String[] cidrBlocks = cidrRange.split("/");
		if (!isValidIp(cidrBlocks[0]))
			throw new IllegalArgumentException(cidrRange + " is not a valid CIDR notation");
		if(Integer.parseInt(cidrBlocks[1])>32)
			throw new IllegalArgumentException(cidrRange + " is not a valid CIDR notation");

		String binaryCidr = decimalToBinary(cidrBlocks[0]);
		String networkPrefix = cidrBlocks[1];
		String binaryIpAddress = decimalToBinary(ipAddress);
		char[] arr_ipAdd = binaryIpAddress.toCharArray();
		char[] arr_cidr = binaryCidr.toCharArray();
		for (int i = 0; i < Integer.parseInt(networkPrefix); i++) {
			if (arr_cidr[i] != arr_ipAdd[i])
				return false;
		}
		return true;
	}
	
	/**
	 * To check if each block in the IPaddress and CIDR is valid
	 * 
	 * @param ipAddress
	 * @return 
	 */

	public static boolean isValidIp(String ipAddress) {
		String[] ipBlocks = ipAddress.split("\\.");
		for (String block : ipBlocks) {
			int i = Integer.parseInt(block);
			if (i > 255)
				return false;
		}
		return true;
	}
	
	/**
	 * To convert decimal format IPaddress and CIDR to 32 bit binary format
	 * 
	 * @param ipAddress
	 * @return
	 */

	public static String decimalToBinary(String ipAddress) {
		String[] ipBlocks = ipAddress.split("\\.");
		StringBuilder binaryString = new StringBuilder();
		for (String block : ipBlocks) {
			String binaryBlock = Integer.toBinaryString(Integer.parseInt(block));
			if (8-binaryBlock.length() == 0) {
				binaryString.append(binaryBlock);
			} else {
				StringBuilder temp = new StringBuilder("00000000");
				temp.replace(8-binaryBlock.length(), temp.length(), binaryBlock);
				binaryString.append(temp);
			}
		}
		return binaryString.toString();
	}

	public static void main(String[] args) {
		System.out.println(validateIpAddress("192.168.1.21", "192.168.0.31/24"));
		//System.out.println(validateIpAddress("192.168.1.321", "192.168.0.31/24"));
		//System.out.println(validateIpAddress("192.168.1.21", "192.168.0.31/243"));
		//System.out.println(validateIpAddress("192.168.1.21", "192.168.0.31/37"));
		System.out.println(validateIpAddress("192.168.0.21", "192.168.0.31/24"));
	}
}
