package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.entity.TripEntity;
import com.weekytripstravelcrm.model.TripModel;
import com.weekytripstravelcrm.service.TripService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trip")
@SecurityRequirement(name = "test-swagger")
public class TripController {
    Logger log = LoggerFactory.getLogger(TripController.class);
    @Autowired
    private TripService tripService;

    @PostMapping(value = "/add-trip" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addTrip(@RequestBody TripModel tripModel){
        this.tripService.addTrip(tripModel);
        log.debug("Trip Added.");
        return ResponseEntity.ok("Trip Added");
    }

    @GetMapping(value = "/get-trip/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public TripEntity getTrip(@PathVariable(name="id") String id){
        TripEntity trip = this.tripService.getTripById(id);
        log.debug("Trip Fetched");
        return trip;
    }

    @GetMapping(value = "/get-trip" , produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TripEntity> getAllTrip(){
        List<TripEntity> trip = this.tripService.getAllTrip();
        log.debug("Trips Fetched");
        return trip;
    }

    @DeleteMapping("delete-trip/{id}"  )
    public ResponseEntity<String> deleteTrip(@PathVariable(name = "id") String id){
        this.tripService.deleteTrip(id);
        log.debug("Trip Deleted");
        return ResponseEntity.ok("Trip deleted successfully.");
    }

    @PutMapping(value = "/update-trip/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public TripEntity updateProduct(@PathVariable(name="id") String id, @RequestBody TripModel updatedTrip) {
        TripEntity trip = this.tripService.updateTrip(id, updatedTrip);
        log.debug("Trip Updated.");
        return trip;
    }


}
