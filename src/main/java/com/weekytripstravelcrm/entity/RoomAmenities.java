package com.weekytripstravelcrm.entity;

import com.weekytripstravelcrm.repository.RoomDetailsRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room_amenities")
public class RoomAmenities {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long roomAmenitiesId;

	@Column
	private String roomAmenitiesType;

	@Column
	private String description;


}
