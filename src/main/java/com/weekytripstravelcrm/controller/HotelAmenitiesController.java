package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.model.HotelAmenitiesModel;

import com.weekytripstravelcrm.service.HotelAmenitiesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;


@RestController
@RequestMapping(value = "/hotelAmenities")
@SecurityRequirement(name = "test-swagger")
public class HotelAmenitiesController {
    Logger log = LoggerFactory.getLogger(HotelAmenitiesController.class);
    @Autowired
    private HotelAmenitiesService hotelAmenitiesService;



    @PostMapping(value = "/save")
    public  String saveHotelAmenities(@RequestBody HotelAmenitiesModel hotelAmenitiesModel,
                                      @RequestParam("hotelName") String hotelName) {
        String message = this.hotelAmenitiesService.saveHotelAmenities(hotelAmenitiesModel , hotelName );
        return message;
    }


    /**
     *
     * @param id
     * @return single HotelAmenitiesModel providing amenities id
     */
    @GetMapping(value = "/get/{id}")
    public HotelAmenitiesModel getHotelAmenitiesById(@PathVariable("id") long id){
        HotelAmenitiesModel hotelAmenitiesModel = this.hotelAmenitiesService.getHotelAmenitiesById(id);
        return  hotelAmenitiesModel;
    }

    /**
     *
     * @param id
     * @param hotelAmenitiesModel
     * @return updates the single hotel amenities by amenities id
     */
    @PutMapping("updateAmenities/{id}")
    public String updateHotelAmenities(@PathVariable("id") long id, @RequestBody HotelAmenitiesModel hotelAmenitiesModel){
        String message = this.hotelAmenitiesService.updateHotelAmenitiesById(id, hotelAmenitiesModel);
        log.info("Successfully updated hotel amenities details by id : " + id);
        return message;
    }

    /**
     *
     * @param id
     * @return delete single hotel amenities by amenities id
     */
    @DeleteMapping("/{id}")
    public  String deleteHotelAmenitiesById(@PathVariable("id") long id, @RequestParam("hotelName")String hotelName){
        String message = this.hotelAmenitiesService.deleteHotelAmenitiesById(id, hotelName);
        log.info("Successfully deleted location details by id : " + id);
        return  message;
    }


}
