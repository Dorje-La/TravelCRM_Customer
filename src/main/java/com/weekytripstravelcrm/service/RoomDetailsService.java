package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.Hotel;
import com.weekytripstravelcrm.entity.RoomAmenities;
import com.weekytripstravelcrm.entity.RoomDetails;
import com.weekytripstravelcrm.exception.HotelNotFoundException;
import com.weekytripstravelcrm.exception.RoomDetailsNotFoundException;
import com.weekytripstravelcrm.model.RoomAmenitiesModel;
import com.weekytripstravelcrm.model.RoomDetailsModel;
import com.weekytripstravelcrm.repository.RoomDetailsRepository;
import com.weekytripstravelcrm.repository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomDetailsService {
    Logger log = LoggerFactory.getLogger(RoomDetailsService.class);
    @Autowired
    private RoomDetailsRepository roomDetailsRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public String saveRoomDetails(RoomDetailsModel roomDetailsModel, String hotelName) {
        Hotel hotel = this.hotelRepository.findByHotelName(hotelName);
        if(hotel==null){
            throw new HotelNotFoundException();
        }
        RoomDetails roomDetails = new RoomDetails();
        roomDetails.setRoomType(roomDetailsModel.getRoomType());
        roomDetails.setRoomAvailable(roomDetailsModel.isRoomAvailable());
        roomDetails.setArea(roomDetailsModel.getArea());
        roomDetails.setCost(roomDetailsModel.getCost());
        roomDetails.setBedInfo(roomDetailsModel.getBedInfo());
        hotel.getRoomDetails().add(roomDetails);

        try {
            this.hotelRepository.save(hotel);
        } catch (Exception e) {
            log.error("Failed to save room details" +e.getMessage());
            return "Failed to save room details";
        }

        return "Room Detail Save Successfully";

    }

    public RoomDetailsModel getRoomDetailsById(Long roomDetailsId) {
        RoomDetails roomDetails = this.roomDetailsRepository.findByRoomId(roomDetailsId);
        if(roomDetails==null){
            throw new RoomDetailsNotFoundException("Room Details not found with this id" + roomDetailsId);
        }
        RoomDetailsModel roomDetailsModel = new RoomDetailsModel();
        roomDetailsModel.setRoomType(roomDetails.getRoomType());
        roomDetailsModel.setRoomAvailable(roomDetails.isRoomAvailable());
        roomDetailsModel.setCost(roomDetails.getCost());
        roomDetailsModel.setArea(roomDetails.getArea());
        roomDetailsModel.setBedInfo(roomDetails.getBedInfo());

        Set<RoomAmenities> roomAmenitiesSet = roomDetails.getRoomAmenities();
        for(RoomAmenities roomAmenities:roomAmenitiesSet){
            RoomAmenitiesModel roomAmenitiesModel = new RoomAmenitiesModel();
            roomAmenitiesModel.setDescription(roomAmenities.getDescription());
            roomAmenitiesModel.setRoomAmenitiesType(roomAmenities.getRoomAmenitiesType());
            roomDetailsModel.getRoomAmenities().add(roomAmenitiesModel);
        }

        return roomDetailsModel;

    }

    public Set<RoomDetailsModel> getRoomDetailsByHotel(String hotelName) {
        Set<RoomDetailsModel> roomDetailsModelSet = new HashSet<>();
        Hotel hotel = this.hotelRepository.findByHotelName(hotelName);
        if(hotel==null){
            throw new RoomDetailsNotFoundException("Room Details not found");
        }
        Set<RoomDetails> roomDetailsSet = hotel.getRoomDetails();
        for(RoomDetails roomDetails: roomDetailsSet){
            RoomDetailsModel roomDetailsModel = new RoomDetailsModel();
            roomDetailsModel.setRoomType(roomDetails.getRoomType());
            roomDetailsModel.setRoomAvailable(roomDetails.isRoomAvailable());
            roomDetailsModel.setCost(roomDetails.getCost());
            roomDetailsModel.setArea(roomDetails.getArea());
            roomDetailsModel.setBedInfo(roomDetails.getBedInfo());

            Set<RoomAmenities> roomAmenitiesSet = roomDetails.getRoomAmenities();
            for(RoomAmenities roomAmenities:roomAmenitiesSet){
                RoomAmenitiesModel roomAmenitiesModel = new RoomAmenitiesModel();
                roomAmenitiesModel.setDescription(roomAmenities.getDescription());
                roomAmenitiesModel.setRoomAmenitiesType(roomAmenities.getRoomAmenitiesType());
                roomDetailsModel.getRoomAmenities().add(roomAmenitiesModel);
            }
            roomDetailsModelSet.add(roomDetailsModel);
        }


        return roomDetailsModelSet;

    }

   public List<RoomDetailsModel> getAllRoomDetails() {
        List<RoomDetailsModel> roomDetailsModelList = new ArrayList<>();
        List<RoomDetails> roomDetailsList = this.roomDetailsRepository.findAll();

       for (RoomDetails roomDetails : roomDetailsList) {
            if (roomDetails != null) {
               RoomDetailsModel roomDetailsModel = new RoomDetailsModel();

               roomDetailsModel.setRoomType(roomDetails.getRoomType());

               roomDetailsModelList.add(roomDetailsModel);
            } else {
               throw new RoomDetailsNotFoundException("Room details not found in the list");
                }
       }

       return roomDetailsModelList;
   }

    public String getRoomDetailsByType(String roomType) {
       RoomDetails roomDetails = (RoomDetails) this.roomDetailsRepository.findByRoomType(roomType);

       if (roomDetails != null) {
           RoomDetailsModel roomDetailsModel = new RoomDetailsModel();

        } else {
            throw new RoomDetailsNotFoundException("Room details not found with Type: " + roomType);
       }
        return "Room Details found" ;
    }



    public String deleteRoomDetails(long roomDetailsId, String hotelName) {
        Hotel hotel = this.hotelRepository.findByHotelName(hotelName);
        if (hotel == null) {
            throw new HotelNotFoundException();
        }
        RoomDetails roomDetails = this.roomDetailsRepository.findByRoomId(roomDetailsId);
        if (roomDetails.getRoomType() == null) {
            throw new RoomDetailsNotFoundException("Room Details not found");
        }
        hotel.getRoomDetails().remove(roomDetails);
        this.hotelRepository.save(hotel);
        this.roomDetailsRepository.delete(roomDetails);
        return "Room Details Deleted Successfully";
    }

    public String updateRoomDetails(long roomDetailsId, RoomDetailsModel updatedRoomDetailsModel) {
        RoomDetails roomDetails = this.roomDetailsRepository.findByRoomId(roomDetailsId);
        if (roomDetails == null) {
            throw new RoomDetailsNotFoundException("Room Details not found with this id " + roomDetailsId);
        }
        roomDetails.setRoomType(updatedRoomDetailsModel.getRoomType());
        roomDetails.setRoomAvailable(updatedRoomDetailsModel.isRoomAvailable());
        roomDetails.setCost(updatedRoomDetailsModel.getCost());
        roomDetails.setArea(updatedRoomDetailsModel.getArea());
        roomDetails.setBedInfo(updatedRoomDetailsModel.getBedInfo());

        try {
            this.roomDetailsRepository.save(roomDetails);
        } catch (Exception e) {
            log.error("Failed to update room details with id " + roomDetailsId + ": " + e.getMessage());
            return "Failed to update room details";
        }

        return "Room Details Updated Successfully";
    }


}
