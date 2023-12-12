package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository  extends JpaRepository<Location, Long> {
//    List<Location> findAllByHotelId(long hotelId);

}
