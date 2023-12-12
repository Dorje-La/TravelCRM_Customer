package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.entity.HotelAddress;
import com.weekytripstravelcrm.model.HotelAddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelAddressRepository extends JpaRepository<HotelAddress, Long> {

     List<HotelAddress> findAllByCity(String city);
}
