package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.entity.RoomDetails;
import com.weekytripstravelcrm.exception.RoomAmenitiesNotFoundException;
import com.weekytripstravelcrm.model.RoomAmenitiesModel;
import com.weekytripstravelcrm.service.RoomAmenitiesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/roomAmenities")
@SecurityRequirement(name = "test-swagger")
public class RoomAmenitiesController {

    @Autowired
    private RoomAmenitiesService roomAmenitiesService;

    @PostMapping(value = "/saveRoomAmenities")
    public String saveRoomAmenities(@RequestBody RoomAmenitiesModel roomAmenitiesModel) {
        String message = this.roomAmenitiesService.saveRoomAmenities(roomAmenitiesModel);
        return message;
    }

    @GetMapping(value = "/displayRoomAmenities/{roomAmenitiesId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RoomAmenitiesModel displayRoomAmenitiesById(@PathVariable("roomAmenitiesId") long roomAmenitiesId) {
        RoomAmenitiesModel roomAmenitiesModel = this.roomAmenitiesService.getRoomAmenitiesById(roomAmenitiesId);
        return roomAmenitiesModel;
    }

    @GetMapping(value = "/fetchRoomAmenities")
    public Set<RoomAmenitiesModel> getRoomAmenitiesByRoom(@RequestParam(name = "hotelName") String  hotelName) {
        Set<RoomAmenitiesModel> roomAmenitiesModelList = this.roomAmenitiesService.getRoomAmenitiesByRoom(hotelName);
        return roomAmenitiesModelList;
    }

    @GetMapping("/displayAllRoomAmenities")
    public List<RoomAmenitiesModel> displayAllRoomAmenities() {
        List<RoomAmenitiesModel> roomAmenitiesModelList = this.roomAmenitiesService.getAllRoomAmenities();
        return roomAmenitiesModelList;
    }
    @DeleteMapping("/deleteRoomAmenities/{roomAmenitiesId}/{roomId}")
    public ResponseEntity<String> deleteRoomAmenities(
            @PathVariable("roomAmenitiesId") long roomAmenitiesId,
            @PathVariable("roomId") long roomId
    ) {
        try {
            this.roomAmenitiesService.deleteRoomAmenitiesById(roomAmenitiesId);
            return ResponseEntity.ok("Room Amenities deleted successfully");
        } catch (RoomAmenitiesNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Room Amenities not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting Room Amenities");
        }
    }



    @PutMapping("/updateRoomAmenities/{roomAmenitiesId}")
    public String updateRoomAmenities(@PathVariable("roomAmenitiesId") long roomAmenitiesId, @RequestBody RoomAmenitiesModel updatedRoomAmenitiesModel) {
        String message = this.roomAmenitiesService.updateRoomAmenities(roomAmenitiesId, updatedRoomAmenitiesModel);
        return message;
    }
}
