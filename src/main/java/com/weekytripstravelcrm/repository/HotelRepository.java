package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.entity.HotelAddress;
import com.weekytripstravelcrm.model.HotelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weekytripstravelcrm.entity.Hotel;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {

     Hotel findByHotelName(String hotelName);
     Hotel findByHotelAddress(HotelAddress address);

     Hotel findByHotelId(long hotelId);
    Hotel findByHotelId(String hotelId);


    @Query("SELECT MAX(CAST(SUBSTRING(h.hotelId, 3) AS long)) FROM Hotel h")
    Long findMaxHotelIdAsLong();
}
