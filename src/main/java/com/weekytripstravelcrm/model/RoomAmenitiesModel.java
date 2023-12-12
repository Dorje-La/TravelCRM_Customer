package com.weekytripstravelcrm.model;

import java.util.Set;

import com.weekytripstravelcrm.entity.RoomAmenities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomAmenitiesModel {
    private String roomAmenitiesType;
    private String description;

}
