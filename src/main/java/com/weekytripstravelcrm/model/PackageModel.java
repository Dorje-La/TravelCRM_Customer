package com.weekytripstravelcrm.model;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PackageModel {
	private String packageName;
	private String city;
	
	private String description;
	
	private Integer travelDays;
	
	private List<ItineraryModel> itinerary;

	private String image;
	
	private String inclusions;
	
	
	private String exclusions;
	
	private String cancellationPolicy;
	
	private String importantNotes;
	
	private Date startDate;

	private Date endDate;
	
	private Double totalPackageCost;
	
	private Double commisionPercent;
	
	private Double discountPercent;


}
