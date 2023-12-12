package com.weekytripstravelcrm.model;

import com.weekytripstravelcrm.entity.HotelAmenities;
import com.weekytripstravelcrm.entity.RoomDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class HotelModel {
	private String hotelName;
	private HotelAddressModel hotelAddress;
	private String emails;
	private String contacts;
	private String managerName;
	private String images;
	private String description;
	private String cancelationPolicy;
	private Set<HotelAmenitiesModel> hotelAmenities =new HashSet<>();
	private  Set<LocationModel> location =new HashSet<>();
	private Set<PropertyRuleModel> propertyRule =new HashSet<>();
	private Set<RoomDetails> roomDetails = new HashSet<>();

}
