package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.entity.RoomAmenities;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomAmenitiesRepository extends JpaRepository<RoomAmenities, Long> {


//    Object findByRoomAmenitiesitiesType(String anyString);

    RoomAmenities findByRoomAmenitiesId(long roomAmenitiesId);
}
