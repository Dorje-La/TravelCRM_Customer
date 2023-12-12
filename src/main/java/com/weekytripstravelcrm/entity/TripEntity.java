package com.weekytripstravelcrm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
@Getter
@Setter
public class TripEntity {
	@Id
	@Column(name="trip_id")
	private String tripId;
	@Column
	private String tripName;
	@Column
	private String tripLocation;
	@Column
	private String description;
	@Column
	private String image;
}
