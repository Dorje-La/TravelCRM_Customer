package com.weekytripstravelcrm_customer.service;

import com.weekytripstravelcrm.entity.Hotel;
import com.weekytripstravelcrm.entity.Location;
import com.weekytripstravelcrm.exception.LocationNotFoundException;
import com.weekytripstravelcrm.model.HotelModel;
import com.weekytripstravelcrm.model.LocationModel;
import com.weekytripstravelcrm.repository.HotelRepository;
import com.weekytripstravelcrm.repository.LocationRepository;
import com.weekytripstravelcrm.service.LocationService;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceTest {
    private MockMvc mockMvc;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    LocationModel locationModel;

    private HotelModel hotelModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        hotelModel = new HotelModel();
        locationModel = new LocationModel();
        this.mockMvc = MockMvcBuilders.standaloneSetup(locationService).build();
        locationModel.setLocationName("Kathmandu");
        locationModel.setDistance("20.0");
        hotelModel.getLocation().add(locationModel);
    }

    @Test
    public void saveLocation_success() throws Exception {
        String hotelName = "ABC";
        Hotel hotel = new Hotel();

        Location location = new Location();
        Mockito.when(hotelRepository.findByHotelName(hotelName)).thenReturn(hotel);
        Mockito.when(hotelRepository.save(hotel)).thenReturn(hotel);
        String msg = this.locationService.saveLocationData(locationModel, hotelName);
        assertEquals(msg, "Successfully saved location");
    }

    @Test
    public void saveLocationData_failure() throws Exception {
        Hotel hotel = new Hotel();
        when(hotelRepository.findByHotelName("Solti Hotel")).thenReturn(hotel);
        Mockito.lenient().when(locationRepository.save(Mockito.any(Location.class))).thenReturn(null);
        String message = this.locationService.saveLocationData(locationModel, "Solti Hotel");
        assertNotEquals(message, "Failed to saved location into database");

    }


    @Test
    public void getLocationById_success() {
        long id = 1;
        Location location = new Location();
        location.setLocationName("Kathmandu");
        location.setDistance("2 mile ");

        when(this.locationRepository.findById(id)).thenReturn(Optional.of(location));
        LocationModel response = this.locationService.getLocationById(id);
        assertEquals(location.getLocationName(), response.getLocationName());
    }

    @Test
    public void getLocationById_notFound() {
        long locationId = 1L;
        when(locationRepository.findById(locationId)).thenThrow(new LocationNotFoundException());
        // Act and Assert
        Assert.assertThrows(LocationNotFoundException.class, () -> {this.locationService.getLocationById(locationId);
        });
    }

    @Test
    public void updateLocation_success() {
        long id = 1L;
        LocationModel updatedLocationModel = new LocationModel();
        updatedLocationModel.setLocationName("Pokhara");
        updatedLocationModel.setDistance("20 miles");

        Location existingLocation = new Location();
        existingLocation.setLocationId(1L);
        existingLocation.setLocationName("Kathmandu");
        existingLocation.setDistance("18 miles");



        when(locationRepository.findById(id)).thenReturn(Optional.of(existingLocation));
        when(locationRepository.save(Mockito.any(Location.class))).thenReturn(existingLocation);
        String response = locationService.updateLocationById(id, updatedLocationModel);
        assertEquals(response, "Successfully updated location");
    }


    @Test
    public void updateLocationById_NotFound() {

        long id = 1L;

        LocationModel updatedLocationModel = new LocationModel();
        updatedLocationModel.setLocationName("Pokhara");
        updatedLocationModel.setDistance("20");

        when(locationRepository.findById(id)).thenThrow(new LocationNotFoundException());

        assertThrows(LocationNotFoundException.class, () -> {
            locationService.updateLocationById(id, updatedLocationModel);
        });
    }



    @Test
    public void deleteLocation_success() {
        long locationId = 1L;
        String hotelName = "Hayat Hotel";

        Hotel hotel = new Hotel();
        Location location = new Location();
        location.setLocationId(locationId);

        when(hotelRepository.findByHotelName(hotelName)).thenReturn(hotel);
        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));
        String response = locationService.deleteLocationById(locationId, hotelName);
        assertEquals("Location successfully deleted", response);
    }


    @Test
    public void deleteLocation_notFound() {
        String hotelName = "Hayat Hotel";
        long locationId = 1L;

        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelName);

        when(hotelRepository.findByHotelName(hotelName)).thenReturn(hotel);
        when(locationRepository.findById(locationId)).thenReturn(Optional.empty());

        assertThrows(LocationNotFoundException.class, () -> {
            locationService.deleteLocationById(locationId, hotelName);
        });
    }

}





