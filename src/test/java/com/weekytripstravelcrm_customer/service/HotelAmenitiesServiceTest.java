package com.weekytripstravelcrm_customer.service;

import com.weekytripstravelcrm.entity.Hotel;
import com.weekytripstravelcrm.entity.HotelAmenities;
import com.weekytripstravelcrm.exception.HotelAmenitiesNotFoundException;
import com.weekytripstravelcrm.exception.HotelNotFoundException;
import com.weekytripstravelcrm.model.HotelAddressModel;
import com.weekytripstravelcrm.model.HotelAmenitiesModel;
import com.weekytripstravelcrm.model.HotelModel;
import com.weekytripstravelcrm.repository.HotelAmenitiesRepository;
import com.weekytripstravelcrm.repository.HotelRepository;
import com.weekytripstravelcrm.service.HotelAmenitiesService;
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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HotelAmenitiesServiceTest {
    private MockMvc mockMvc;
    @Mock
    private HotelAmenitiesRepository hotelAmenitiesRepository;
    @InjectMocks
    private HotelAmenitiesService hotelAmenitiesService;
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private HotelModel hotelModel;
    private HotelAmenitiesModel hotelAmenitiesModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(hotelAmenitiesService).build();
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
        hotelAmenitiesModel = new HotelAmenitiesModel();
        hotelAmenitiesModel.setAmenitiesName("ABC");
        hotelAmenitiesModel.setDescription("Description");
        hotelModel.getHotelAmenities().add(hotelAmenitiesModel);
    }

    @Test
    public void hotelAmenitiesSave_success() {
        HotelAmenities savedHo = new HotelAmenities();
        String hotelName = "Yak";
        Hotel hotel = new Hotel();

        when(hotelRepository.findByHotelName(hotelName)).thenReturn(hotel);
        when(hotelRepository.save(hotel)).thenReturn(hotel);
        String message = this.hotelAmenitiesService.saveHotelAmenities(hotelAmenitiesModel, hotelName);

        Assert.assertEquals(message, "Successfully saved");
    }

    @Test
    public void hotelAmenitiesSave_failure() {
        String hotelName = "ABC";
        Hotel hotel = new Hotel();
        Mockito.lenient().when(hotelRepository.save(hotel)).thenThrow(new HotelNotFoundException());
        Assert.assertThrows(HotelNotFoundException.class, () -> {
            hotelAmenitiesService.saveHotelAmenities(hotelAmenitiesModel, hotelName);
        });
    }

    @Test
    public void getHotelAmenitiesById_success() {
        long id = 1;
        HotelAmenities hotelAmenities = new HotelAmenities();
        hotelAmenities.setAmenitiesName("ABC");
        hotelAmenities.setDescription("Description");

        when(hotelAmenitiesRepository.findByHotelAmenityId(id)).thenReturn(hotelAmenities);
        HotelAmenitiesModel hotelAmenitiesModel1 = this.hotelAmenitiesService.getHotelAmenitiesById(id);

        Assert.assertEquals(hotelAmenitiesModel1.getAmenitiesName(), hotelAmenitiesModel.getAmenitiesName());
        Assert.assertEquals(hotelAmenitiesModel1.getDescription(), hotelAmenitiesModel.getDescription());
    }

    @Test
    public void getHotelAmenitiesById_notFound() {
        Long id = 9L;

        Mockito.lenient().when(hotelAmenitiesRepository.findById(id)).thenThrow(new HotelAmenitiesNotFoundException());
        Assert.assertThrows(HotelAmenitiesNotFoundException.class, () -> {
            hotelAmenitiesService.getHotelAmenitiesById(id);
        });
    }


    @Test
    public void updateHotelAmenities_success() {
        long id = 1L;
        HotelAmenities hotelAmenities = new HotelAmenities();
        hotelAmenitiesModel.setDescription("Updated Description");
        HotelAmenities existingHotelAmenities = new HotelAmenities();
        existingHotelAmenities.setDescription("Original Description");

        Mockito.when(hotelAmenitiesRepository.findByHotelAmenityId(id)).thenReturn(existingHotelAmenities);
        Mockito.lenient().when(hotelAmenitiesRepository.save(hotelAmenities)).thenReturn(hotelAmenities);
        String response = hotelAmenitiesService.updateHotelAmenitiesById(id, hotelAmenitiesModel);
        Assert.assertEquals(response, "Successfully updated");
    }

    @Test
    public void updateHotelAmenitiesById_notFound() {
        long id = 1L;
        HotelAmenitiesModel hotelAmenities = new HotelAmenitiesModel();
        hotelAmenities.setAmenitiesName("Updated Rule Topics");
        hotelAmenities.setDescription("Updated Description");
        Mockito.when(hotelAmenitiesRepository.findByHotelAmenityId(id)).thenThrow(new HotelAmenitiesNotFoundException());
        Assert.assertThrows(HotelAmenitiesNotFoundException.class, () -> {
            hotelAmenitiesService.updateHotelAmenitiesById(id, hotelAmenities);
        });
    }

    @Test
    public void deleteHotelAmenities_success() {
        long id = 1L;
        String hotelName = "Yak";
        HotelAmenities hotelAmenities = new HotelAmenities();
        Hotel hotel = new Hotel();
        hotel.getHotelAmenities().remove(hotelAmenities);
        Mockito.when(hotelRepository.findByHotelName(hotelName)).thenReturn(hotel);
        Mockito.when(hotelAmenitiesRepository.findByHotelAmenityId(id)).thenReturn(hotelAmenities);
        String message = hotelAmenitiesService.deleteHotelAmenitiesById(id, hotelName);
        Assert.assertEquals(message, "Hotel Amenities Deleted Successfully");
    }

    @Test
    public void deleteHotelAmenities_failed() {
        long id = 1L;
        String hotelName = "Yak";
        Hotel hotel = new Hotel();
        when(hotelRepository.findByHotelName(hotelName)).thenReturn(hotel);
        when(hotelAmenitiesRepository.findByHotelAmenityId(id)).thenThrow(new HotelAmenitiesNotFoundException());
        Assert.assertThrows(HotelAmenitiesNotFoundException.class, () -> {
            hotelAmenitiesService.deleteHotelAmenitiesById(id, hotelName);
        });
    }
}