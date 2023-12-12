package com.weekytripstravelcrm.controller;


import com.weekytripstravelcrm.model.PropertyRuleModel;
import com.weekytripstravelcrm.service.PropertyRuleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping(value = "/propertyRule")
@SecurityRequirement(name = "test-swagger")
    public class PropertyRuleController {
         Logger log = LoggerFactory.getLogger(PropertyRuleController.class);
        @Autowired
        private PropertyRuleService propertyRuleService;

    @PostMapping(value = "/savePropertyRule", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String savePropertyRule(@RequestBody PropertyRuleModel propertyruleModel, @RequestParam("hotelName") String hotelName) {
        String message = this.propertyRuleService.savePropertyRule(propertyruleModel, hotelName);
        log.info("PropertyRule data saved");
        return  message;

    }
        @GetMapping(value = "/getPropertyRule/{id}")
        public PropertyRuleModel getPropertyRuleById(@PathVariable("id") long id){
            PropertyRuleModel propertyRuleModel = this.propertyRuleService.getPropertyRuleById(id);
            log.info("Successfully get PropertyRule details by id : " + id);
            return  propertyRuleModel;
        }

    @GetMapping(value = "/getPropertyRuleList")
    public Set<PropertyRuleModel> getPropertyRuleList(@RequestParam("hotelName") String hotelName){
        Set<PropertyRuleModel> propertyRuleModelSet = this.propertyRuleService.getListOfPropertyRuleByHotel(hotelName);
        log.info("PropertyRuleModel list data successfully get "+ hotelName);
        return propertyRuleModelSet;
    }

        @PutMapping("updatePropertyRule/{id}")
        public String updatePropertyRule(@PathVariable("id") long id, @RequestBody PropertyRuleModel propertyRuleModel){
            String message = this.propertyRuleService.updatePropertyRuleById(id, propertyRuleModel);
            log.info("Successfully updated PropertyRule details by id : " + id);
            return message;
        }


        @DeleteMapping("/delete/{id}")
        public  String deletePropertyRule(@PathVariable("id") long id,  @RequestParam("hotelName") String hotelName){
            String message = this.propertyRuleService.deletePropertyRuleById(id, hotelName);
            log.info("Successfully deleted PropertyRule details by id : " + id);
            return  message;
        }
}
