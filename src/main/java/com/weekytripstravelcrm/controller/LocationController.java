package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.model.LocationModel;
import com.weekytripstravelcrm.service.LocationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Set;

@RestController
@RequestMapping(value = "/location")
@SecurityRequirement(name = "test-swagger")
public class LocationController {
    Logger log = LoggerFactory.getLogger(LocationController.class);
    @Autowired
    private LocationService locationService;


    @PostMapping(value = "/saveLocationData", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveLocationData(@RequestBody LocationModel locationModel, @PathParam("hotelName") String hotelName) {
        String message = locationService.saveLocationData(locationModel, hotelName);
        log.info("Location data saved");
        return  message;

    }

    @GetMapping(value = "/getLocation/{id}")
    public LocationModel getLocationById(@PathVariable("id") long id){
        LocationModel locationModel = this.locationService.getLocationById(id);
        log.info("Location data successfully get "+ id);
        return  locationModel;
    }

    @GetMapping(value = "/getLocationList")
    public Set<LocationModel> getLocationListByHotel(@RequestParam("hotelName") String hotelName){
        Set<LocationModel> locationModelSet = this.locationService.getListOfLocationByHotelName(hotelName);
        log.info("Location list data successfully get "+ hotelName);
        return locationModelSet;
    }

    @PutMapping("updateLocation/{id}")
    public String updateLocation(@PathVariable("id") long id, @RequestBody LocationModel locationModel){

        String message = this.locationService.updateLocationById(id, locationModel);
        log.info("Successfully updated location details by id : " + id);
        return message;
    }

    @DeleteMapping("/delete/{id}")
    public  String deleteLocation(@PathVariable("id") long id, @RequestParam("hotelName") String hotelName){
        String message = this.locationService.deleteLocationById(id, hotelName);
        log.info("Successfully deleted location details by id : " + id);
        return  message;
    }
}
