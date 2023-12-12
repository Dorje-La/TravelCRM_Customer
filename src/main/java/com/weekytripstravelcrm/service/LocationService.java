package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.Hotel;
import com.weekytripstravelcrm.entity.Location;
import com.weekytripstravelcrm.exception.HotelNotFoundException;
import com.weekytripstravelcrm.exception.LocationNotFoundException;
import com.weekytripstravelcrm.exception.NullException;
import com.weekytripstravelcrm.model.LocationModel;
import com.weekytripstravelcrm.repository.HotelRepository;
import com.weekytripstravelcrm.repository.LocationRepository;
import com.weekytripstravelcrm.util.HotelValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class LocationService {
    Logger log = LoggerFactory.getLogger(LocationService.class);
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public String saveLocationData(LocationModel locationModel, String hotelName) {
        Hotel hotel = this.hotelRepository.findByHotelName(hotelName);
        Location location = new Location();
        if (hotel == null) {
            throw new HotelNotFoundException();
        }
        if (!HotelValidationUtil.isLocationModelValid(locationModel)) {
            throw new NullException("Location items cannot be null");
        }
        location.setLocationName(locationModel.getLocationName());
        location.setDistance(locationModel.getDistance());
        hotel.getLocation().add(location);

        try {
            this.hotelRepository.save(hotel);
        } catch (Exception e) {
            log.error("Failed to save location " + e.getMessage());
            return "Failed to saved location into database";
        }


        return "Successfully saved location";

    }

    public LocationModel getLocationById(long locationId) {
        LocationModel locationModel = new LocationModel();
        Location location = this.locationRepository.findById(locationId).get();
        if (location == null) {
            log.error("Location not found from given information " + locationId);
            throw new LocationNotFoundException();
        }
        locationModel.setLocationName(location.getLocationName());
        locationModel.setDistance(location.getDistance());
        return locationModel;
    }

    public Set<LocationModel> getListOfLocationByHotelName(String hotelName) {
        Hotel hotel = this.hotelRepository.findByHotelName(hotelName);
        Set<LocationModel> locationModelSet = new HashSet<>();
        Set<Location> locationSet = hotel.getLocation();
        for (Location location : locationSet) {
            if (location.getLocationId() == null) {
                throw new LocationNotFoundException();
            }
            LocationModel locationModel = new LocationModel();
            locationModel.setLocationName(location.getLocationName());
            locationModel.setDistance(location.getDistance());
            locationModelSet.add(locationModel);
        }
        return locationModelSet;
    }

    public String updateLocationById(long locationId, LocationModel locationModel) {
        Location location = this.locationRepository.findById(locationId).get();
        if (location == null) {
            throw new LocationNotFoundException();
        }
        if (!HotelValidationUtil.isLocationModelValid(locationModel)) {
            throw new NullException("Location items cannot be null");
        }
        location.setLocationName(locationModel.getLocationName());
        location.setDistance(locationModel.getDistance());
        try {
            this.locationRepository.save(location);
        } catch (Exception e) {
            log.error("Failed to update location into database " + e.getMessage());
            return "Failed to update location into database";
        }
        return "Successfully updated location";
    }

    public String deleteLocationById(long locationId, String hotelName) {
        Hotel hotel = this.hotelRepository.findByHotelName(hotelName);
        if (hotel == null) {
            throw new HotelNotFoundException();
        }
        Optional<Location> locationObj = this.locationRepository.findById(locationId);
        if (locationObj.isEmpty()) {
            log.error("Location information not found by id" + locationId);
            throw new LocationNotFoundException();
        }
        Location location = locationObj.get();
        hotel.getLocation().remove(location);
        try {
            this.hotelRepository.save(hotel);
            this.locationRepository.delete(location);
        } catch (Exception e) {
            log.error("Location information not deleted from database " + e.getMessage());
            return "Location cannot deleted from database";
        }
        return "Location successfully deleted";
    }

}




