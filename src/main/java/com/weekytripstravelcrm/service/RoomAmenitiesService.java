package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.Hotel;
import com.weekytripstravelcrm.entity.RoomAmenities;
import com.weekytripstravelcrm.entity.RoomDetails;
import com.weekytripstravelcrm.exception.RoomAmenitiesNotFoundException;
import com.weekytripstravelcrm.model.RoomAmenitiesModel;
import com.weekytripstravelcrm.model.RoomDetailsModel;
import com.weekytripstravelcrm.repository.HotelRepository;
import com.weekytripstravelcrm.repository.RoomAmenitiesRepository;
import com.weekytripstravelcrm.repository.RoomDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomAmenitiesService {

    @Autowired
private  RoomAmenitiesRepository roomAmenitiesRepository;

    @Autowired

    private RoomDetailsRepository roomDetailsRepository;

    @Autowired
    private HotelRepository hotelRepository;


    public String saveRoomAmenities(RoomAmenitiesModel roomAmenitiesModel) {
        RoomAmenities roomAmenities = new RoomAmenities();
        roomAmenities.setRoomAmenitiesType(roomAmenitiesModel.getRoomAmenitiesType());
        roomAmenities.setDescription(roomAmenitiesModel.getDescription());

        try {
            this.roomAmenitiesRepository.save(roomAmenities);
            return "Room Amenities Successfully Saved";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Failed to save room amenities";
        }
    }
    public RoomAmenitiesModel getRoomAmenitiesById(Long roomAmenitiesId) {
        RoomAmenitiesModel roomAmenitiesModel = new RoomAmenitiesModel();
        Optional<RoomAmenities> roomAmenitiesObject = this.roomAmenitiesRepository.findById(roomAmenitiesId);

        if (roomAmenitiesObject.isPresent()) {
            RoomAmenities roomAmenities = roomAmenitiesObject.get();
            roomAmenitiesModel.setRoomAmenitiesType(roomAmenities.getRoomAmenitiesType());
            roomAmenitiesModel.setDescription(roomAmenities.getDescription());
        } else {
            throw new RoomAmenitiesNotFoundException("Amenities not found for room with ID: " + roomAmenitiesId);
        }

        return roomAmenitiesModel;
    }


    public List<RoomAmenitiesModel> getAllRoomAmenities() {
        List<RoomAmenitiesModel> roomAmenitiesModelList = new ArrayList<>();
        List<RoomAmenities> roomAmenitiesList = this.roomAmenitiesRepository.findAll();

        for (RoomAmenities roomAmenities : roomAmenitiesList) {
            if (roomAmenities != null) {
                RoomAmenitiesModel roomAmenitiesModel = new RoomAmenitiesModel();
                roomAmenitiesModel.setRoomAmenitiesType(roomAmenities.getRoomAmenitiesType());
                roomAmenitiesModel.setDescription(roomAmenities.getDescription());

                roomAmenitiesModelList.add(roomAmenitiesModel);
            } else {
                throw new RoomAmenitiesNotFoundException("Room amenities not found in the list");
            }
        }

        return roomAmenitiesModelList;
    }



    public Set<RoomAmenitiesModel> getRoomAmenitiesByRoom(String hotelName) {
        Hotel hotel = this.hotelRepository.findByHotelName(hotelName);
        Set<RoomDetails> roomDetailsSet = hotel.getRoomDetails();
        Set<RoomAmenitiesModel> roomAmenitiesModelSet = new HashSet<>();
        for(RoomDetails roomDetails : roomDetailsSet){
            RoomDetailsModel roomDetailsModel = new RoomDetailsModel();
            Set<RoomAmenities> roomAmenitiesSet = roomDetails.getRoomAmenities();
                for(RoomAmenities roomAmenities: roomAmenitiesSet){
                    RoomAmenitiesModel roomAmenitiesModel = new RoomAmenitiesModel();
                    roomAmenitiesModel.setRoomAmenitiesType(roomAmenities.getRoomAmenitiesType());
                    roomAmenitiesModel.setDescription(roomAmenities.getDescription());
                    roomAmenitiesModelSet.add(roomAmenitiesModel);
                }
        }

        return roomAmenitiesModelSet;
    }
    public void deleteRoomAmenitiesById(Long id) {
        Optional<RoomAmenities> roomAmenitiesOptional = roomAmenitiesRepository.findById(id);
        if (roomAmenitiesOptional.isPresent()) {
            roomAmenitiesRepository.deleteById(id);
        } else {
            throw new RoomAmenitiesNotFoundException("Room Amenities with ID " + id + " not found");
        }
    }


    public String updateRoomAmenities(long roomAmenitiesId, RoomAmenitiesModel updatedRoomAmenitiesModel) {
        Optional<RoomAmenities> roomAmenitiesObject = this.roomAmenitiesRepository.findById(roomAmenitiesId);

        if (roomAmenitiesObject.isPresent()) {
            RoomAmenities roomAmenities = roomAmenitiesObject.get();
           roomAmenities.setRoomAmenitiesType(updatedRoomAmenitiesModel.getRoomAmenitiesType());
            this.roomAmenitiesRepository.save(roomAmenities);

            return "Room Amenities Successfully Updated";
        } else {
            throw new RoomAmenitiesNotFoundException("Amenities not found for room with ID: " + roomAmenitiesId);
        }
    }





}
