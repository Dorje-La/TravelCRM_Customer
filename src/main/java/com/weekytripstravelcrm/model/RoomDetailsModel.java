package com.weekytripstravelcrm.model;

import com.weekytripstravelcrm.entity.RoomAmenities;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoomDetailsModel {
    private String roomType;
    private String area;
    private String bedInfo;
    private Double cost;
    private boolean roomAvailable;
    private  Set<RoomAmenitiesModel> roomAmenities = new HashSet<>();

}
