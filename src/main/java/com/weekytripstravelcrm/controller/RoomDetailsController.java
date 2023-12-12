package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.model.RoomDetailsModel;
import com.weekytripstravelcrm.service.RoomDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping(value = "/roomDetails")
@SecurityRequirement(name = "test-swagger")
public class RoomDetailsController {

    private final Logger log = LoggerFactory.getLogger(RoomDetailsController.class);

    @Autowired
    private RoomDetailsService roomDetailsService;

    @PostMapping(value = "/saveRoomDetails")
    public String saveRoomDetails(@RequestBody RoomDetailsModel roomDetailsModel, @RequestParam("hotelName") String hotelName) {
        String message = this.roomDetailsService.saveRoomDetails(roomDetailsModel, hotelName);
        return message;
    }

    @GetMapping(value = "/getRoomDetail/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RoomDetailsModel displayRoomDetailsById(@PathVariable("roomId") long roomId) {
        RoomDetailsModel roomDetailsModel = this.roomDetailsService.getRoomDetailsById(roomId);
        return roomDetailsModel;
    }

    @GetMapping(value = "/getRoomDetailsByHotel", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<RoomDetailsModel> displayRoomDetailsByHotel(@PathParam("hotelName") String hotelName) {
        Set<RoomDetailsModel> roomDetailsModelSet = this.roomDetailsService.getRoomDetailsByHotel(hotelName);
        return roomDetailsModelSet;
    }

    @GetMapping("/displayAllRoomDetails")
    public ResponseEntity<List<RoomDetailsModel>> displayAllRoomDetails() {
        List<RoomDetailsModel> roomDetailsModelList;

        try {
            roomDetailsModelList = roomDetailsService.getAllRoomDetails();
        } catch (Exception e) {
            log.error("An error occurred while fetching room details: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }

        if (roomDetailsModelList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(roomDetailsModelList);
        }
    }



    @DeleteMapping("/deleteRoomDetails/{roomDetailsId}")
    public String deleteRoomDetails(@PathVariable("roomDetailsId") long roomDetailsId ,@RequestParam("hotelName") String hotelName) {
        String message = this.roomDetailsService.deleteRoomDetails(roomDetailsId , hotelName);
        return message;
    }

    @PutMapping("/updateRoomDetails/{roomDetailsId}")
    public String updateRoomDetails(@PathVariable("roomDetailsId") long roomDetailsId, @RequestBody RoomDetailsModel updatedRoomDetailsModel) {
        String message = this.roomDetailsService.updateRoomDetails(roomDetailsId, updatedRoomDetailsModel);
        return message;
    }
}
