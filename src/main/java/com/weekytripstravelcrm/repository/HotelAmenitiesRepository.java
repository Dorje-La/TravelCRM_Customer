package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.entity.Hotel;
import com.weekytripstravelcrm.entity.HotelAmenities;
import com.weekytripstravelcrm.entity.PropertyRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface HotelAmenitiesRepository extends JpaRepository<HotelAmenities, Long> {


    HotelAmenities findAllByHotelAmenityId(long id);

    HotelAmenities findByHotelAmenityId(long id);

    HotelAmenities findByAmenitiesName(String amenitiesName);
}
