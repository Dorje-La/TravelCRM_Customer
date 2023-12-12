package com.weekytripstravelcrm_customer.service;

import com.weekytripstravelcrm.entity.TripEntity;
import com.weekytripstravelcrm.exception.TripNotFoundException;
import com.weekytripstravelcrm.model.TripModel;
import com.weekytripstravelcrm.repository.TripRepository;
import com.weekytripstravelcrm.service.TripService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
public class TripServiceTest {

    private MockMvc mockMvc;

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripService tripService;
    private TripModel tripModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(tripService).build();
        tripModel = new TripModel();
        tripModel.setTripName("Nepal");
        tripModel.setTripLocation("Pokhara");
        tripModel.setDescription("Sightseeing in Pokhara Valley");
        tripModel.setImage("img.jpg");
    }

    @Test
    public void addTrip_success() throws Exception {

        TripEntity trip = new TripEntity();
        Mockito.lenient().when(tripRepository.save(trip)).thenReturn(trip);
        Mockito.lenient().when(tripRepository.findMaxTripIdAsLong()).thenReturn(1001L);
        String msg = this.tripService.addTrip(tripModel);
        Assert.assertEquals(msg, "success");
    }

    @Test
    public void addTrip_failure() throws Exception {
        tripModel.setTripName(null);
        TripEntity trip = new TripEntity();
        Mockito.lenient().when(tripRepository.save(trip)).thenReturn(trip);
        String msg = this.tripService.addTrip(tripModel);
        Assert.assertEquals(msg, "failure");
    }


    @Test
    public void deleteTrip_success() throws Exception{
        String id = "TPR1000";
        TripEntity trip = new TripEntity();
        Mockito.lenient().when(tripRepository.findById(id)).thenReturn(Optional.of(trip));
        String result = this.tripService.deleteTrip(id);
        Assert.assertEquals(result, "success");

    }

    @Test
    public void deleteTrip_failure() throws Exception{
        String id = "TPR1000";
        Mockito.lenient().doThrow(new TripNotFoundException()).when(tripRepository).deleteById(id);
        Assert.assertThrows(TripNotFoundException.class, () -> {
            tripService.deleteTrip(id);
        });
    }
    @Test
    public  void getTripById_success() throws Exception {
        String id = "TPR1001" ;
        TripEntity trip = new TripEntity();
        trip.setTripName("Nepal");
        trip.setTripLocation("Pokhara");
        trip.setDescription("Sightseeing in Pokhara Valley");
        trip.setImage("img.jpg");
        Mockito.lenient().when(tripRepository.findById(id)).thenReturn(Optional.of(trip));
        TripEntity newTrip = this.tripService.getTripById(id);
        Assert.assertEquals(newTrip.getTripId() , trip.getTripId());
        Assert.assertEquals(newTrip.getTripName() , trip.getTripName());
        Assert.assertEquals(newTrip.getDescription() , trip.getDescription());
        Assert.assertEquals(newTrip.getImage() , trip.getImage());

    }

    @Test
    public void getTripById_failure() throws Exception {
        String id = "TPR1001" ;
        Mockito.lenient().when(tripRepository.findById(id)).thenThrow(new TripNotFoundException());
        Assertions.assertThrows(TripNotFoundException.class, () -> {
            tripService.getTripById(id);
        });
    }

    @Test
    public void updateTrip_success() throws Exception{
        String id = "TPR1001" ;
        TripEntity trip = new TripEntity();
        trip.setTripName("Nepal");
        trip.setTripLocation("Pokhara");
        trip.setDescription("Sightseeing in Pokhara Valley");
        trip.setImage("img.jpg");
        Mockito.lenient().when(tripRepository.findById(id)).thenReturn(Optional.of(trip));
        Mockito.lenient().when(tripRepository.save(trip)).thenReturn(trip);
        TripEntity newTrip = this.tripService.updateTrip(id , tripModel);
        Assert.assertEquals(newTrip.getTripId() , trip.getTripId());
        Assert.assertEquals(newTrip.getTripName() , trip.getTripName());
        Assert.assertEquals(newTrip.getDescription() , trip.getDescription());
        Assert.assertEquals(newTrip.getImage() , trip.getImage());
        Assert.assertEquals(this.tripService.updateTrip(id, tripModel), trip);
    }
    @Test
    public void updateTrip_failure() throws Exception {
        String id = "TPR1001" ;
        Mockito.lenient().when(tripRepository.findById(id)).thenThrow(new TripNotFoundException());
        Assertions.assertThrows(TripNotFoundException.class, () -> {
            tripService.updateTrip(id , tripModel);
        });
    }
    @Test
    public void getAll_success() throws Exception {
        List<TripEntity> trips = new ArrayList<>();
        TripEntity trip = new TripEntity();
        trip.setTripName("Nepal");
        trip.setTripLocation("Pokhara");
        trip.setDescription("Sightseeing in Pokhara Valley");
        trip.setImage("img.jpg");
        trips.add(trip);
        Mockito.lenient().when(tripRepository.findAll()).thenReturn(trips);
        List<TripEntity> newTrips = new ArrayList<>();
        newTrips = this.tripService.getAllTrip();
        Assert.assertEquals(newTrips.get(0).getTripId() , trips.get(0).getTripId());
        Assert.assertEquals(newTrips.get(0).getTripName() , trips.get(0).getTripName());
        Assert.assertEquals(newTrips.get(0).getDescription() , trips.get(0).getDescription());
        Assert.assertEquals(newTrips.get(0).getImage() , trips.get(0).getImage());
    }

    @Test
    public void getAll_failure() throws Exception {
        Mockito.lenient().when(tripRepository.findAll()).thenThrow(new TripNotFoundException());
        Assertions.assertThrows(TripNotFoundException.class, () -> {
            tripService.getAllTrip();
        });

    }
}