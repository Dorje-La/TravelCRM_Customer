package com.weekytripstravelcrm.util;

import com.weekytripstravelcrm.model.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HotelValidationUtil {
	public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
	public static final String PHONE_REGEX = "^\\(\\d{3}\\)-\\d{3}-\\d{4}$|^\\d{3}-\\d{3}-\\d{4}$|^\\d{10}$";

	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean isPhoneNumberValid(String phoneNumber) {
		Pattern pattern = Pattern.compile(PHONE_REGEX);
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}

	public static boolean isHotelModelValid(HotelModel model) {
		return !isStringNullOrEmpty(model.getHotelName()) &&
				!isStringNullOrEmpty(model.getContacts()) &&
				!isStringNullOrEmpty(model.getCancelationPolicy()) &&
				!isStringNullOrEmpty(model.getEmails()) &&
				!isStringNullOrEmpty(model.getImages())&&
				!isStringNullOrEmpty(model.getManagerName());
	}

	public static boolean isAddressModelValid(HotelAddressModel model) {
		return !isStringNullOrEmpty(model.getStreetName()) &&
				!isStringNullOrEmpty(model.getCity()) &&
				!isStringNullOrEmpty(model.getState()) &&
				!isStringNullOrEmpty(model.getZip()) &&
				!isStringNullOrEmpty(model.getCountry());
	}

	public static boolean isHotelAmenitiesModelValid(HotelAmenitiesModel model) {
		return isStringNullOrEmpty(model.getAmenitiesName()) ||
				isStringNullOrEmpty(model.getDescription());
	}

	public static boolean isLocationModelValid(LocationModel model) {
		return !isStringNullOrEmpty(model.getDistance()) &&
				!isStringNullOrEmpty(model.getLocationName());
	}

	public static boolean isPropertyRuleModelValid(PropertyRuleModel model) {
		return !isStringNullOrEmpty(model.getRuleTopics()) &&
				!isStringNullOrEmpty(model.getDescription());
	}

	private static boolean isStringNullOrEmpty(String toCheck){
		return toCheck == null ||
				toCheck.isEmpty() ||
				toCheck.isBlank();
	}

}
