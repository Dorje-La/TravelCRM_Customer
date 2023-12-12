package com.weekytripstravelcrm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelAmenities {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hotelAmenityId;
    @Column
    private String amenitiesName;
    @Column
    private String description;

  


}
