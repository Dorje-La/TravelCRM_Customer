package com.weekytripstravelcrm.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "hotel")
public class Hotel {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private String hotelId;
	@Column
	private String hotelName;
	@OneToOne(cascade = CascadeType.ALL)
	private HotelAddress hotelAddress;
	@Column
	private String emails;
	@Column
	private String contacts;
	@Column
	private String managerName;
	@Column
	private String images;
	@Column
	private String description;
	@Column
	private String cancelationPolicy;

	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "hotel_amenities_mapping",
			joinColumns = {@JoinColumn(name = "hotelId")},
			inverseJoinColumns = {@JoinColumn(name = "hotelAmenityId")})
	private Set<HotelAmenities> hotelAmenities =new HashSet<>();

	@ManyToMany(cascade =CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "hotel_property_rule_mapping",
			joinColumns = {@JoinColumn(name = "hotelId")},
			inverseJoinColumns = {@JoinColumn(name = "ruleId")})
	private Set<PropertyRule> propertyRule =new HashSet<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "hotel_location_mapping",
			joinColumns = {@JoinColumn(name = "hotelId")},
			inverseJoinColumns = {@JoinColumn(name = "locationId")})
	private Set<Location> location = new HashSet<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "hotel_room_mapping",
			joinColumns = {@JoinColumn(name = "hotelId")},
			inverseJoinColumns = {@JoinColumn(name = "roomId")})
	private Set<RoomDetails> roomDetails = new HashSet<>();

}
