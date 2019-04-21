package com.unc.hospitalschool.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FieldValidation {

	private static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	/*
	 * fieldName - in case of an exception, this value will be sent in an HTTP Error Response
	 * fieldValue - the value to check
	 */
	public static boolean parseBoolean(String fieldName, String fieldValue) throws Exception {
		if (fieldValue.equals("true") || fieldValue.equals("false")) {
			return Boolean.parseBoolean(fieldValue);
		} else {
			throw new Exception("Field " + fieldName + " must be boolean");
		}
	}

	/*
	 * fieldName - in case of an exception, this value will be sent in an HTTP Error Response
	 * fieldValue - the value to check
	 */
	public static int parseInt(String fieldName, String fieldValue) throws Exception {
		try {
			return Integer.parseInt(fieldValue);
		} catch (Exception e) {
			throw new Exception("Field " + fieldName + " must be int");
		}
	}
	
	/*
	 * Compares that start date is before, or the same day as end date
	 */
	public static boolean compareDates(String start, String end) throws Exception{
		formatter.setLenient(false);
		try {
			Date date1 = formatter.parse(start);
			Date date2 = formatter.parse(end);
			return date1.before(date2) || date1.equals(date2);
		} catch (Exception e) {
			throw new Exception("Dates must be formatted (yyyy/mm/dd)");
		}		
	}
	
	public static boolean dateValidation(String input) {
		formatter.setLenient(false);
		try {
			formatter.parse(input);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
