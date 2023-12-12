package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.Hotel;
import com.weekytripstravelcrm.entity.HotelAmenities;
import com.weekytripstravelcrm.exception.CustomException;
import com.weekytripstravelcrm.exception.HotelAmenitiesNotFoundException;
import com.weekytripstravelcrm.exception.HotelNotFoundException;
import com.weekytripstravelcrm.exception.NullException;
import com.weekytripstravelcrm.model.HotelAmenitiesModel;
import com.weekytripstravelcrm.repository.HotelAmenitiesRepository;
import com.weekytripstravelcrm.repository.HotelRepository;
import com.weekytripstravelcrm.util.HotelValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HotelAmenitiesService {
    Logger log = LoggerFactory.getLogger(HotelAmenitiesService.class);

    @Autowired
    private HotelAmenitiesRepository hotelAmenitiesRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public String saveHotelAmenities(HotelAmenitiesModel hotelAmenitiesModel, String hotelName) {
        HotelAmenities hotelAmenities = new HotelAmenities();
        Hotel hotel = this.hotelRepository.findByHotelName(hotelName);
        if (hotel == null) {
            throw new HotelNotFoundException();
        }
        if(!HotelValidationUtil.isHotelAmenitiesModelValid(hotelAmenitiesModel)){
            throw new NullException("Null value cannot be passed");
        }
        Set<HotelAmenities> amenitiesSet = hotel.getHotelAmenities();
        for(HotelAmenities hotelAmenities1 : amenitiesSet){
            if(hotelAmenities1.getAmenitiesName().equals(hotelAmenitiesModel.getAmenitiesName())){
                throw new CustomException("Hotel Amenities name already found in database");
            }
        }
        hotelAmenities.setAmenitiesName(hotelAmenitiesModel.getAmenitiesName());
        hotelAmenities.setDescription(hotelAmenitiesModel.getDescription());
        hotel.getHotelAmenities().add(hotelAmenities);

       try{
           this.hotelRepository.save(hotel);
       }catch (Exception e){
           log.error("Failed to save Hotel Amenities into database " + e.getMessage());
           return "Failed to save Hotel Amenities into database";
       }
        return "Successfully saved";
    }

    public HotelAmenitiesModel getHotelAmenitiesById(long id) {
        HotelAmenitiesModel hotelAmenitiesModel = new HotelAmenitiesModel();
        HotelAmenities hotelAmenities = this.hotelAmenitiesRepository.findAllByHotelAmenityId(id);
        if(hotelAmenities.getHotelAmenityId()==null){
            throw new HotelAmenitiesNotFoundException();
        }
        hotelAmenitiesModel.setAmenitiesName(hotelAmenities.getAmenitiesName());
        hotelAmenitiesModel.setDescription(hotelAmenities.getDescription());

        hotelAmenitiesModel.setAmenitiesName(hotelAmenities.getAmenitiesName());
        hotelAmenitiesModel.setDescription(hotelAmenities.getDescription());

        return hotelAmenitiesModel;
    }

    public String deleteHotelAmenitiesById(long id, String hotelName) {
        Hotel hotel = this.hotelRepository.findByHotelName(hotelName);
        if (hotel == null) {
            throw new HotelNotFoundException();
        }

        HotelAmenities hotelAmenities = this.hotelAmenitiesRepository.findByHotelAmenityId(id);
        if (hotelAmenities == null) {
            throw new HotelAmenitiesNotFoundException();
        }

        hotel.getHotelAmenities().remove(hotelAmenities);
        this.hotelRepository.save(hotel);
        this.hotelAmenitiesRepository.delete(hotelAmenities);

        return "Hotel Amenities Deleted Successfully";
    }

    public String updateHotelAmenitiesById(long id, HotelAmenitiesModel hotelAmenitiesModel) {
        HotelAmenities hotelAmenities = this.hotelAmenitiesRepository.findByHotelAmenityId(id);
        if (hotelAmenities == null) {
            throw new HotelAmenitiesNotFoundException();
        }

        hotelAmenities.setAmenitiesName(hotelAmenitiesModel.getAmenitiesName());
        hotelAmenities.setDescription(hotelAmenitiesModel.getDescription());
        try{
            this.hotelAmenitiesRepository.save(hotelAmenities);
            log.info("Successfully updated hotel amenities by id : " + id);
        }catch (Exception e){
            log.info("Failed to updated hotel amenities : " + e.getMessage());
            return "Failed to updated hotel amenities";
        }
        this.hotelAmenitiesRepository.save(hotelAmenities);

        log.info("Successfully updated hotel amenities by id: " + id);
        return "Successfully updated";
    }

    public List<HotelAmenitiesModel> getAllHotelAmenities() {
        List<HotelAmenities> allAmenities = this.hotelAmenitiesRepository.findAll();
        return allAmenities.stream()
                .map(amenity -> {
                    HotelAmenitiesModel model = new HotelAmenitiesModel();
                    model.setAmenitiesName(amenity.getAmenitiesName());
                    model.setDescription(amenity.getDescription());
                    return model;
                })
                .collect(Collectors.toList());
    }
}
