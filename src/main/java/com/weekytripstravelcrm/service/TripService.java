package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.util.KeyGenerator;
import com.weekytripstravelcrm.entity.TripEntity;
import com.weekytripstravelcrm.exception.TripNotFoundException;
import com.weekytripstravelcrm.model.TripModel;
import com.weekytripstravelcrm.repository.TripRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Service
public class TripService {
    Logger log = LoggerFactory.getLogger(TripService.class);
    @Autowired
    private TripRepository tripRepository;


    /**
     *
     * @param tripModel
     * @return
     */
    public String addTrip(TripModel tripModel) {
        Long id = this.tripRepository.findMaxTripIdAsLong();
        String newId= KeyGenerator.generateId("TR" , id , TripEntity.class , 120923L );
        TripEntity trip = new TripEntity();

        if(tripModel.getTripName() == null || tripModel.getTripName().isEmpty()|| tripModel.getTripLocation() == null || tripModel.getTripLocation().isEmpty())
        {
            if(tripModel.getTripName() == null || tripModel.getTripName().isEmpty()){
                throw new NullPointerException("Trip name is Null or Empty");
            }
            else if(tripModel.getTripLocation() == null || tripModel.getTripLocation().isEmpty()){
                throw new NullPointerException("Trip location is Null or Empty");
            }
        }
        trip.setTripId(newId);
        trip.setTripName(tripModel.getTripName());
        trip.setTripLocation(tripModel.getTripLocation());
        trip.setDescription(tripModel.getDescription());
        trip.setImage(tripModel.getImage());
        this.tripRepository.save(trip);
        return "success";

    }

    public TripEntity getTripById(String id) {
        Optional<TripEntity> trip= this.tripRepository.findById(id);
        if(trip.isPresent()){
            return trip.get();
        }else{
            log.debug("Unable to find the trip matching the id provided");
            throw new TripNotFoundException();
        }
    }

    public String deleteTrip(String id) {

        Optional<TripEntity> existingProduct = this.tripRepository.findById(id);
        if(existingProduct.isPresent()){
            this.tripRepository.deleteById(id);
            return "success";
        }else{
            log.debug("Trip does not exist");
            throw new TripNotFoundException();
        }

    }

    public TripEntity updateTrip(String id , TripModel updatedTrip) {
        Optional<TripEntity> existingProduct = this.tripRepository.findById(id);
        if(existingProduct.isEmpty() ){
            log.debug("Trip cannot be updated since it does not exist.");
            throw new TripNotFoundException();
        }else{
            TripEntity trip = existingProduct.get();
            if(updatedTrip.getTripName()!=null)
                trip.setTripName(updatedTrip.getTripName());
            if(updatedTrip.getTripLocation()!=null)
                trip.setTripLocation(updatedTrip.getTripLocation());
            if(updatedTrip.getDescription()!=null)
                trip.setDescription(updatedTrip.getDescription());
            if(updatedTrip.getImage()!=null)
                trip.setImage(updatedTrip.getImage());
            return this.tripRepository.save(trip);
        }

    }

    public List<TripEntity> getAllTrip() {
        Iterable<TripEntity> trips = this.tripRepository.findAll();
        List<TripEntity> list = new ArrayList<>();
        for (TripEntity item : trips) {
            list.add(item);
        }
        if(list.isEmpty()){
            log.debug("There are no available trips");
            throw new TripNotFoundException();
        }
        return list;
    }
}
