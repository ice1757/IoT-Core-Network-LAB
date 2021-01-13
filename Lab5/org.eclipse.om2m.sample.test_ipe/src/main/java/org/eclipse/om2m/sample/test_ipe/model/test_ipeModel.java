package org.eclipse.om2m.sample.test_ipe.model;

import org.eclipse.om2m.commons.exceptions.BadRequestException;

public class test_ipeModel {

	// init
	private static boolean AIRCON = false;
	private static int AIRCON_temp = 25;
	private static int AIRCON_fan = 1;
	// ·s¼Wªº
	private static int AIRCON_fan_max = 5;

	private test_ipeModel() {
	}

	// Sets the direct current Air Conditioner state
	public static void setAirConON() {
		// write your code here
		if (AIRCON == true)
			throw new BadRequestException("AirCON is open");
		AIRCON = true;
	}

	public static void setAirConOFF() {
		// write your code here
		if (AIRCON == false)
			throw new BadRequestException("AirCON is open");
		AIRCON = false;
	}

	public static boolean setTempPlus() {
		// write your code here
		if (getAirConValue() == false)
			return false;
		AIRCON_temp++;
		return true;
	}

	public static boolean setTempMinus() {
		// write your code here
		if (getAirConValue() == false)
			return false;
		AIRCON_temp--;
		return true;
	}

	public static boolean setFanPlus() {
		// write your code here
		if (getAirConFan() == AIRCON_fan_max || getAirConValue() == false)
			return false;
		AIRCON_fan++;
		return true;
	}

	public static boolean setFanMinus() {
		// write your code here
		if (getAirConFan() == 1 || getAirConValue() == false) 
			return false;
		AIRCON_fan--;
		return true;
	}

	// Gets the direct current Air Conditioner state

	public static boolean getAirConValue() {
		return AIRCON;
	}

	public static int getAirConTemp() {
		return AIRCON_temp;
	}

	public static int getAirConFan() {
		return AIRCON_fan;
	}
}