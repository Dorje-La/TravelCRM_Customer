package com.weekytripstravelcrm.service;
import com.weekytripstravelcrm.entity.Hotel;
import com.weekytripstravelcrm.entity.PropertyRule;
import com.weekytripstravelcrm.exception.HotelNotFoundException;
import com.weekytripstravelcrm.exception.NullException;
import com.weekytripstravelcrm.exception.PropertyRuleNotFoundException;
import com.weekytripstravelcrm.model.PropertyRuleModel;
import com.weekytripstravelcrm.repository.HotelRepository;
import com.weekytripstravelcrm.repository.PropertyRuleRepository;
import com.weekytripstravelcrm.util.HotelValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PropertyRuleService {
    Logger log = LoggerFactory.getLogger(PropertyRuleService.class);

    @Autowired
    private PropertyRuleRepository propertyRuleRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public String savePropertyRule(PropertyRuleModel propertyRuleModel, String hotelName) {
        Hotel hotel = this.hotelRepository.findByHotelName(hotelName);
        PropertyRule propertyRule = new PropertyRule();
        if(hotel==null) {
            throw new HotelNotFoundException();
        }
        if(!HotelValidationUtil.isPropertyRuleModelValid(propertyRuleModel)){
            throw new NullException("Property rule item cannot be null");
        }
        propertyRule.setRuleTopics(propertyRuleModel.getRuleTopics());
        propertyRule.setDescription(propertyRuleModel.getDescription());
            hotel.getPropertyRule().add(propertyRule);

          try {
              this.hotelRepository.save(hotel);
          }catch (Exception e){
              log.error("Failed to save property rule" + e.getMessage());
              return "Failed to saved property rule into database";
          }

        return "Success";
    }


    public PropertyRuleModel getPropertyRuleById(long ruleId){
        PropertyRuleModel propertyRuleModel = new PropertyRuleModel();
        PropertyRule propertyRule = this.propertyRuleRepository.findById(ruleId).get();
        if(propertyRule == null){
            log.error("Property not found exception");
            throw new PropertyRuleNotFoundException();
        }
            propertyRuleModel.setRuleTopics(propertyRule.getRuleTopics());
            propertyRuleModel.setDescription(propertyRule.getDescription());
        return  propertyRuleModel;
    }

    public Set<PropertyRuleModel> getListOfPropertyRuleByHotel(String hotelName) {
        Hotel hotel = this.hotelRepository.findByHotelName(hotelName);

        Set<PropertyRuleModel> propertyRuleModelSet = new HashSet<>();
        Set<PropertyRule> propertyRuleSet = hotel.getPropertyRule();
        for(PropertyRule propertyRule : propertyRuleSet) {
            if(propertyRule == null){
                throw new PropertyRuleNotFoundException();
            }
            PropertyRuleModel propertyRuleModel = new PropertyRuleModel();
            propertyRuleModel.setRuleTopics(propertyRule.getRuleTopics());
            propertyRuleModel.setDescription(propertyRule.getDescription());
            propertyRuleModelSet.add(propertyRuleModel);
        }
        return propertyRuleModelSet;
    }


    public String updatePropertyRuleById(long ruleId , PropertyRuleModel propertyRuleModel){
        PropertyRule propertyRule = this.propertyRuleRepository.findById(ruleId).get();
        if(propertyRule.getRuleId() == null){
            throw new PropertyRuleNotFoundException();
        }
        if(!HotelValidationUtil.isPropertyRuleModelValid(propertyRuleModel)){
            throw new NullException("Property rule item cannot be null");
        }
        propertyRule.setRuleTopics(propertyRuleModel.getRuleTopics());
        propertyRule.setDescription(propertyRuleModel.getDescription());
        try {
            this.propertyRuleRepository.save(propertyRule);
        }catch (Exception e){
            log.error("Failed to update property rule" + e.getMessage());
            return "Failed to update property rule into database";
        }
        return "Successfully updated";
    }

    public String deletePropertyRuleById(long ruleId, String hotelName){
        Hotel hotel = this.hotelRepository.findByHotelName(hotelName);
        if(hotel==null){
            throw new HotelNotFoundException();
        }
        PropertyRule propertyRule = this.propertyRuleRepository.findByRuleId(ruleId);
        if(propertyRule.getRuleId()==null){
            throw  new PropertyRuleNotFoundException();
        }
        hotel.getPropertyRule().remove(propertyRule);
        try {
            this.hotelRepository.save(hotel);
            this.propertyRuleRepository.delete(propertyRule);
        }catch (Exception e){
            log.error("Failed to delete property rule from database" + e.getMessage());
            return "Failed to delete property rule from  database";
        }

        return "Property Rule Deleted Successfully";
    }


}
