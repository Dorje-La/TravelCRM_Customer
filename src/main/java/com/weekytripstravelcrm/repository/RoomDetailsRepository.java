package com.weekytripstravelcrm.repository;

/**
 * @author Samyona
 */

import com.weekytripstravelcrm.entity.RoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomDetailsRepository extends JpaRepository<RoomDetails, Long> {

    RoomDetails findByRoomId(Long roomId);

    RoomDetails findByRoomType(String roomType);


}


