package com.weekytripstravelcrm.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class HotelAddressModel {
	private String streetName;
	private String city;
	private String state;
	private String zip;
	private String country;
}
