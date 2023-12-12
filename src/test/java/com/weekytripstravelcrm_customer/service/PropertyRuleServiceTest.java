package com.weekytripstravelcrm_customer.service;

import com.weekytripstravelcrm.entity.Hotel;
import com.weekytripstravelcrm.entity.PropertyRule;
import com.weekytripstravelcrm.exception.HotelNotFoundException;
import com.weekytripstravelcrm.exception.NullException;
import com.weekytripstravelcrm.exception.PropertyRuleNotFoundException;
import com.weekytripstravelcrm.model.HotelAddressModel;
import com.weekytripstravelcrm.model.HotelModel;
import com.weekytripstravelcrm.model.PropertyRuleModel;
import com.weekytripstravelcrm.repository.HotelRepository;
import com.weekytripstravelcrm.repository.PropertyRuleRepository;
import com.weekytripstravelcrm.service.HotelService;
import com.weekytripstravelcrm.service.PropertyRuleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
   public class PropertyRuleServiceTest {

    private MockMvc mockMvc;
    @Mock
    private PropertyRuleRepository propertyRuleRepository;
    @InjectMocks
    private PropertyRuleService propertyRuleService;
    @Mock
    private HotelRepository hotelRepository;
    private PropertyRuleModel propertyRuleModel;

    @Mock
    private HotelService hotelService;
    @Mock
    private HotelModel hotelModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(propertyRuleService).build();
        hotelModel.setHotelName("Yak");
        hotelModel.setContacts("5163126380");
        hotelModel.setCancelationPolicy("ABCD");
        hotelModel.setDescription("ABCDEF");
        hotelModel.setEmails("shree@gmail.com");

        hotelModel.setImages("img1.jpg");
        hotelModel.setManagerName("CP");

        HotelAddressModel addressModel = new HotelAddressModel();

        addressModel.setStreetName("Alpine");
        addressModel.setCity("Euless");
        addressModel.setState("TX");
        addressModel.setZip("76040");
        addressModel.setCountry("USA");
        hotelModel.setHotelAddress(addressModel);
        propertyRuleModel = new PropertyRuleModel();
        propertyRuleModel.setDescription("A");
        propertyRuleModel.setRuleTopics("S");
        hotelModel.getPropertyRule().add(propertyRuleModel);

    }
    @Test
    public void propertyRule_success() {
        PropertyRule savedPropertyRule = new PropertyRule();
        String hotelName = "Yak";
        Hotel hotel = new Hotel();

        Mockito.when(hotelRepository.findByHotelName(hotelName)).thenReturn(hotel);
        Mockito.when(hotelRepository.save(hotel)).thenReturn(hotel);
        String message = this.propertyRuleService.savePropertyRule(propertyRuleModel, hotelName);

        Assert.assertEquals(message, "Success");
    }


    @Test
    public void savePropertyRule_failure1() {
        // Create a mock PropertyRuleModel
        PropertyRuleModel propertyRuleModel = new PropertyRuleModel();
        String hotelName = "ABC";
        Hotel hotel = new Hotel();
        when(hotelRepository.save(hotel)).thenThrow(new  HotelNotFoundException());
        Assert.assertThrows(HotelNotFoundException.class, ()->{propertyRuleService.savePropertyRule(propertyRuleModel, hotelName);});
    }

    @Test
    public void savePropertyRule_failure2() {

        PropertyRuleModel propertyRuleModel = new PropertyRuleModel();
        String hotelName = "Yak";
        propertyRuleModel.setDescription("");
        Hotel hotel = new Hotel();
        PropertyRule propertyRule = new PropertyRule();
        propertyRule.setDescription("");
        hotel.getPropertyRule().add(propertyRule);
        Mockito.when(hotelRepository.findByHotelName(hotelName)).thenReturn(hotel);
        Mockito.when(hotelRepository.save(hotel)).thenThrow(new NullException("Property rule item cannot be null"));
        Assert.assertThrows(NullException.class, ()->{propertyRuleService.savePropertyRule(propertyRuleModel, hotelName);});
    }

    @Test
    public void getPropertyRuleById_success() {
        long id = 1;

        PropertyRule propertyRule = new PropertyRule();
        propertyRule.setRuleTopics("Test Rule Topics");
        propertyRule.setDescription("Test Description");

        when(propertyRuleRepository.findById(id)).thenReturn(Optional.of(propertyRule));
        PropertyRuleModel propertyRuleModel = propertyRuleService.getPropertyRuleById(id);

        assertEquals("Test Rule Topics", propertyRuleModel.getRuleTopics());
        assertEquals("Test Description", propertyRuleModel.getDescription());
    }


    @Test
    public void getPropertyRuleById_notFound() {
        Long id = 9L;

        when(propertyRuleRepository.findById(id)).thenThrow(new PropertyRuleNotFoundException());
        Assert.assertThrows(PropertyRuleNotFoundException.class, () -> {propertyRuleService.getPropertyRuleById(id);});
    };



    @Test
    public void updatePropertyRule_success() {
        long id = 1L;


        PropertyRuleModel updatedPropertyRuleModel = new PropertyRuleModel();
        updatedPropertyRuleModel.setRuleTopics("Updated Rule Topics");
        updatedPropertyRuleModel.setDescription("Updated Description");

        PropertyRule existingPropertyRule = new PropertyRule();
        existingPropertyRule.setRuleId(id);
        existingPropertyRule.setRuleTopics("Original Rule Topics");
        existingPropertyRule.setDescription("Original Description");

        when(propertyRuleRepository.findById(id)).thenReturn(Optional.of(existingPropertyRule));
        when(propertyRuleRepository.save(Mockito.any(PropertyRule.class))).thenReturn(existingPropertyRule);
        String response = propertyRuleService.updatePropertyRuleById(id, updatedPropertyRuleModel);
        assertEquals( response, "Successfully updated");
    }

    @Test
    public void updatePropertyRule_notFound() {
        long id = 1L;


        PropertyRuleModel updatedPropertyRuleModel = new PropertyRuleModel();
        updatedPropertyRuleModel.setRuleTopics("Updated Rule Topics");
        updatedPropertyRuleModel.setDescription("Updated Description");
        when(propertyRuleRepository.findById(id)).thenThrow(new PropertyRuleNotFoundException());
        Assert.assertThrows(PropertyRuleNotFoundException.class, ()->{propertyRuleService.updatePropertyRuleById(id, propertyRuleModel);});
    }


    @Test
    public void deletePropertyRule_success() {
        long id = 1L;

        PropertyRule propertyRule = new PropertyRule();
        propertyRule.setRuleId(id);
        when(propertyRuleRepository.findById(id)).thenReturn(Optional.of(propertyRule));
        doNothing().when(propertyRuleRepository).delete(propertyRule);
    }

    @Test
    public void deletePropertyRule_notFound() {
        long id = 1L;
        String hotelName = "Yak";
        Hotel hotel = new Hotel();
        when(hotelRepository.findByHotelName(hotelName)).thenReturn(hotel);
        when(propertyRuleRepository.findByRuleId(id)).thenThrow(new PropertyRuleNotFoundException());
        Assert.assertThrows(PropertyRuleNotFoundException.class, ()->{propertyRuleService.deletePropertyRuleById(id, hotelName);});
    }
}

