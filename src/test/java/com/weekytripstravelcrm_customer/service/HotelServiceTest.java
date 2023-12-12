package com.weekytripstravelcrm_customer.service;

import com.weekytripstravelcrm.entity.Hotel;
import com.weekytripstravelcrm.entity.HotelAddress;
import com.weekytripstravelcrm.exception.*;
import com.weekytripstravelcrm.model.HotelAddressModel;
import com.weekytripstravelcrm.model.HotelModel;
import com.weekytripstravelcrm.repository.HotelAddressRepository;
import com.weekytripstravelcrm.repository.HotelRepository;
import com.weekytripstravelcrm.service.HotelService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)

public class HotelServiceTest {
    private MockMvc mockMvc;

    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private HotelAddressRepository hotelAddressRepository;

    @InjectMocks
    private HotelService hotelService;
    private HotelModel hotelModel ;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        hotelModel = new HotelModel();
        this.mockMvc = MockMvcBuilders.standaloneSetup(hotelService).build();
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

    }
    @Test
    public void saveHotel_success() throws Exception {
        Hotel hotel = new Hotel();

        Mockito.lenient().when(this.hotelRepository.save(hotel)).thenReturn(hotel);
        String msg = this.hotelService.saveHotel(hotelModel);
        Assert.assertEquals(msg , "success");
    }

    @Test
    public void saveHotel_failure() throws Exception{
        Hotel hotel = new Hotel();
        hotelModel.setHotelName("");
        Mockito.lenient().when(this.hotelRepository.save(hotel)).thenThrow(new NullException("Hotel information cannot be null"));
        Assert.assertThrows(NullException.class,()-> {hotelService.saveHotel(hotelModel);});
    }

    @Test
    public void saveHotel_ContactNumber_failure() throws Exception{
        Hotel hotel = new Hotel();
        hotelModel.setContacts("51631ghh26380");
        Mockito.lenient().when(this.hotelRepository.save(hotel)).thenThrow(new ContactNumberException());
        Assert.assertThrows(ContactNumberException.class,()-> {hotelService.saveHotel(hotelModel);});
    }

    @Test
    public void saveHotel_Email_failure() throws Exception{
        Hotel hotel = new Hotel();
        hotelModel.setEmails("gmail.com");
        Mockito.lenient().when(this.hotelRepository.save(hotel)).thenThrow(new InvalidEmailException("Invalid Email"));
        Assert.assertThrows(InvalidEmailException.class,()-> {hotelService.saveHotel(hotelModel);});
    }

    @Test
    public void getHotelById_success() {
        String id ="2";
        Hotel hotel = new Hotel();
        hotel.setHotelId(id);
        hotel.setHotelName("Yak");
        hotel.setContacts("5163126380");
        hotel.setCancelationPolicy("ABCD");
        hotel.setDescription("ABCDEF");
        hotel.setEmails("shree@gmail.com");

        hotel.setImages("img1.jpg");
        hotel.setManagerName("CP");

        HotelAddress address = new HotelAddress();

        address.setStreetName("Alpine");
        address.setCity("Euless");
        address.setState("TX");
        address.setZip("76040");
        address.setCountry("USA");
        hotel.setHotelAddress(address);
        Mockito.lenient().when(this.hotelRepository.findByHotelId(id)).thenReturn(hotel);
        Hotel hotel1 = this.hotelService.getByHotelId(id);
        Assert.assertEquals(hotel1.getHotelName(),hotelModel.getHotelName());
    }

    @Test
    public void getHotelById_failure() {
        String id ="101";
        Mockito.when(this.hotelRepository.findByHotelId(id)).thenThrow(new HotelNotFoundException());
        Assert.assertThrows(HotelNotFoundException.class , ()->{
            this.hotelService.getByHotelId(id);
        });
    }

    @Test
    public void getHotelByName_success() {
        String hotelName ="Yak";
        Hotel hotel = new Hotel();
        hotel.setHotelId("HTL101");
        hotel.setHotelName("Yak");
        hotel.setContacts("5163126380");
        hotel.setCancelationPolicy("ABCD");
        hotel.setDescription("ABCDEF");
        hotel.setEmails("shree@gmail.com");

        hotel.setImages("img1.jpg");
        hotel.setManagerName("CP");

        HotelAddress address = new HotelAddress();

        address.setStreetName("Alpine");
        address.setCity("Euless");
        address.setState("TX");
        address.setZip("76040");
        address.setCountry("USA");
        hotel.setHotelAddress(address);
        Mockito.when(this.hotelRepository.findByHotelName(hotelName)).thenReturn(hotel);
        Hotel hotel1 = this.hotelService.getHotelByName(hotelName);
        Assert.assertEquals(hotel1.getHotelName(),hotel.getHotelName());
    }

    @Test
    public void getHotelByName_failure() {
        String hotelName ="Yak";
        Mockito.when(this.hotelRepository.findByHotelName(hotelName)).thenThrow(new HotelNotFoundException());
        Assert.assertThrows(HotelNotFoundException.class , ()->{
            this.hotelService.getHotelByName(hotelName);
        });
    }

    @Test
    public void getHotelByCity_success() {
        String city ="Euless";
        List<Hotel> hotelList = new ArrayList<>();
        List<HotelAddress> addressList = new ArrayList<>();
        String hotelName ="Yak";
        Hotel hotel = new Hotel();
        hotel.setHotelId("HTL101");
        hotel.setHotelName("Yak");
        hotel.setContacts("5163126380");
        hotel.setCancelationPolicy("ABCD");
        hotel.setDescription("ABCDEF");
        hotel.setEmails("shree@gmail.com");

        hotel.setImages("img1.jpg");
        hotel.setManagerName("CP");

        HotelAddress address = new HotelAddress();

        address.setStreetName("Alpine");
        address.setCity("Euless");
        address.setState("TX");
        address.setZip("76040");
        address.setCountry("USA");
        hotel.setHotelAddress(address);
        hotelList.add(hotel);
        addressList.add(address);
        Mockito.when(this.hotelAddressRepository.findAllByCity(city)).thenReturn(addressList);
        Mockito.when(this.hotelRepository.findByHotelAddress(address)).thenReturn(hotel);
        List<Hotel> hotel1 = this.hotelService.getHotelByCity(city);
        Assert.assertEquals(hotel1.size(),hotelList.size());
    }

    @Test
    public void getHotelByCity_failure() {
        String city ="Euless";
        HotelAddress address = new HotelAddress();
        Mockito.when(this.hotelAddressRepository.findAllByCity(city)).thenThrow(new AddressException());
        Mockito.lenient().when(this.hotelRepository.findByHotelAddress(address)).thenThrow(new HotelNotFoundException());
        Assert.assertThrows(AddressException.class , ()->{
            this.hotelService.getHotelByCity(city);
        });
    }

    @Test
    public void getALlHotels_success(){
        List<HotelModel> hotelList = new ArrayList<>();
        Hotel hotel = new Hotel();
        hotel.setHotelId("HTL101");
        hotel.setHotelName("Yak");
        hotel.setContacts("5163126380");
        hotel.setCancelationPolicy("ABCD");
        hotel.setDescription("ABCDEF");
        hotel.setEmails("shree@gmail.com");

        hotel.setImages("img1.jpg");
        hotel.setManagerName("CP");

        HotelAddress address = new HotelAddress();

        address.setStreetName("Alpine");
        address.setCity("Euless");
        address.setState("TX");
        address.setZip("76040");
        address.setCountry("USA");
        hotel.setHotelAddress(address);
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        Mockito.when(this.hotelRepository.findAll()).thenReturn(hotels);
        List<HotelModel> result= this.hotelService.getALlHotels();
        Assert.assertEquals(result.get(0).getHotelName(), hotels.get(0).getHotelName());
        Assert.assertEquals(result.get(0).getContacts(), hotels.get(0).getContacts());
        Assert.assertEquals(result.get(0).getEmails(), hotels.get(0).getEmails());
    }

    @Test
    public void getALlHotels_failure(){
        Mockito.when(this.hotelRepository.findAll()).thenThrow(new NullException("Hotel is null"));
        Assert.assertThrows(NullException.class , ()->{
            this.hotelService.getALlHotels();
        });
    }

    @Test
    public void deleteHotelById_Success() {
        String hotelID = "HTL101";
        Hotel hotel = new Hotel();
        Mockito.lenient().when(hotelRepository.findById(hotelID)).thenReturn(Optional.of(hotel));
        String message = this.hotelService.deleteHotelById(hotelID);
        Assert.assertEquals(message, "Hotel deleted from database");
    }

    @Test
    public void deleteHotelById_failure() {
        String id = "HTL101";
        Mockito.lenient().doThrow(new HotelNotFoundException()).when(hotelRepository).deleteById(id);
        Assert.assertThrows(HotelNotFoundException.class, () -> {
            hotelService.deleteHotelById(id);
        });
    }

    @Test
    public void updateHotelById_success(){
        String hotelId = "HTL1001";
        Hotel hotel = new Hotel();
        hotel.setHotelName("Solti Hotel");
        hotel.setContacts("5163126380");
        hotel.setCancelationPolicy("ABCD");
        hotel.setDescription("ABCDEF");
        hotel.setEmails("shree@gmail.com");

        hotel.setImages("img1.jpg");
        hotel.setManagerName("CP");

        HotelAddress address = new HotelAddress();

        address.setStreetName("Alpine");
        address.setCity("Euless");
        address.setState("TX");
        address.setZip("76040");
        address.setCountry("USA");
        hotel.setHotelAddress(address);

        Mockito.lenient().when(hotelRepository.findByHotelId(hotelId)).thenReturn(hotel);
        Mockito.lenient().when(hotelRepository.save(hotel)).thenReturn(hotel);
        String message = this.hotelService.updateHotelById(hotelId, hotelModel);
        Assert.assertEquals(message, "Hotel Information Successfully Updated");
    }

    @Test
    public void updateHotelById_failure(){
        String hotelID = "HTL101";
        Hotel hotel = new Hotel();
        Mockito.lenient().when(hotelRepository.findByHotelId(hotelID)).thenThrow(new HotelNotFoundException());
        Assert.assertThrows(HotelNotFoundException.class,()-> {hotelService.updateHotelById(hotelID, hotelModel);});
    }



}
