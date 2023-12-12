package com.weekytripstravelcrm_customer.service;

import com.weekytripstravelcrm.entity.ItineraryEntity;
import com.weekytripstravelcrm.entity.PackageEntity;
import com.weekytripstravelcrm.exception.InvalidPackageModelException;
import com.weekytripstravelcrm.model.ItineraryModel;
import com.weekytripstravelcrm.model.PackageModel;
import com.weekytripstravelcrm.repository.PackageRepository;
import com.weekytripstravelcrm.service.PackageService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doAnswer;

@RunWith(MockitoJUnitRunner.class)
public class PackageServiceTest {

    private MockMvc mockMvc;

    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private PackageService packageService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(packageService).build();
    }

    @Test
    public void savePackage_success(){
        //GIVEN a package model
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("Maldives");
        packageModel.setCity("Male");
        packageModel.setDescription("A visit to the  beautiful capital city");
        packageModel.setTravelDays(2);
        packageModel.setInclusions("Ferry to the hotel");
        packageModel.setExclusions("Flight cost");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("No illegal activities allowed");

        /*TripModel trip = new TripModel();
        trip.setTripLocation("Maldives");
        trip.setTripName("Maldives trip");
        trip.setDescription("Trip in maldives");*/
        packageModel.setImage("");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        PackageEntity entity = new PackageEntity();
        entity.setPackageName(packageModel.getPackageName());
        entity.setCity(packageModel.getCity());
        entity.setDescription(packageModel.getDescription());
        entity.setTravelDays(packageModel.getTravelDays());
        entity.setInclusions(packageModel.getInclusions());
        entity.setExclusions(packageModel.getExclusions());
        entity.setCancellationPolicy(packageModel.getCancellationPolicy());
        entity.setImportantNotes(packageModel.getImportantNotes());
//        TripEntity tripEntity = new TripEntity(trip.getTripName(), trip.getTripLocation(), trip.getDescription());
        entity.setImage(packageModel.getImage());
        List<ItineraryEntity> itineraryEntities = new ArrayList<>();
        itineraryEntities.add(new ItineraryEntity());
        entity.setItinery(itineraryEntities);


        final PackageEntity[] packageEntity = {new PackageEntity()};

        Mockito.when(packageRepository.save(ArgumentMatchers.any(PackageEntity.class)))
                .thenAnswer(new Answer<PackageEntity>() {
                    @Override
                    public PackageEntity answer(InvocationOnMock invocationOnMock) throws Throwable {
                        packageEntity[0] = invocationOnMock.getArgument(0);
                        return entity;
                    }
                });

        //WHEN we call the package service to save the package
        PackageModel savedPackage = null;
        try {
            savedPackage = this.packageService.addPackage(packageModel);
        } catch (InvalidPackageModelException e) {
            throw new RuntimeException(e);
        }

        //THEN we should call package repository with correct package information to save in database
        Assert.assertEquals(packageEntity[0].getPackageName(),packageModel.getPackageName());
        Assert.assertEquals(packageEntity[0].getCity(),packageModel.getCity());
        Assert.assertEquals(packageEntity[0].getDescription(),packageModel.getDescription());
        Assert.assertEquals(packageEntity[0].getExclusions(),packageModel.getExclusions());
        Assert.assertEquals(packageEntity[0].getInclusions(),packageModel.getInclusions());
        Assert.assertEquals(packageEntity[0].getCancellationPolicy(),packageModel.getCancellationPolicy());
        Assert.assertEquals(packageEntity[0].getImportantNotes(),packageModel.getImportantNotes());

        //AND we should return the saved package
        Assert.assertEquals(savedPackage.getPackageName(),packageModel.getPackageName());
        Assert.assertEquals(savedPackage.getCity(),packageModel.getCity());
        Assert.assertEquals(savedPackage.getDescription(),packageModel.getDescription());
        Assert.assertEquals(savedPackage.getExclusions(),packageModel.getExclusions());
        Assert.assertEquals(savedPackage.getInclusions(),packageModel.getInclusions());
        Assert.assertEquals(savedPackage.getCancellationPolicy(),packageModel.getCancellationPolicy());
        Assert.assertEquals(savedPackage.getImportantNotes(),packageModel.getImportantNotes());
    }

    @Test
    public void getPackage_success(){
        //GIVEN a package that exists with passed in ID
        String packageId = "123546";
        PackageEntity entity = new PackageEntity();
        entity.setPackageName("KathmanduTrip");
        entity.setCity("Kathmandu");
        entity.setDescription("Visit capital of Nepal");
        entity.setTravelDays(5);
        entity.setInclusions("Inclusions");
        entity.setExclusions("Exclusion");
        entity.setCancellationPolicy("Cancellation Policy");
        entity.setImportantNotes("Some notes");
       // TripEntity tripEntity = new TripEntity("KTM Trip", "Kathmandu", "Visit KTM 2020");
        entity.setImage("");
        List<ItineraryEntity> itineraryEntities = new ArrayList<>();
        itineraryEntities.add(new ItineraryEntity());
        entity.setItinery(itineraryEntities);

        Mockito.when(packageRepository.findById(ArgumentMatchers.anyString()))
                .thenAnswer(new Answer<Optional<PackageEntity>>() {
                    @Override
                    public Optional<PackageEntity> answer(InvocationOnMock invocationOnMock) throws Throwable {
                        String id = invocationOnMock.getArgument(0);

                        if(id.equals(packageId))
                        {
                            return Optional.of(entity);
                        }

                        return null;
                    }
                });

        //WHEN we call the service to get with ID
        var savedPackage = this.packageService.getPackageById(packageId);

        //THEN we should return the expected package
        Assert.assertEquals(savedPackage.getPackageName(),entity.getPackageName());
        Assert.assertEquals(savedPackage.getCity(),entity.getCity());
        Assert.assertEquals(savedPackage.getDescription(),entity.getDescription());
        Assert.assertEquals(savedPackage.getExclusions(),entity.getExclusions());
        Assert.assertEquals(savedPackage.getInclusions(),entity.getInclusions());
        Assert.assertEquals(savedPackage.getCancellationPolicy(),entity.getCancellationPolicy());
        Assert.assertEquals(savedPackage.getImportantNotes(),entity.getImportantNotes());
    }

    @Test
    public void getPackage_failure(){
        //GIVEN a package does not exist
        Mockito.when(packageRepository.findById(ArgumentMatchers.anyString()))
                .thenAnswer(new Answer<Optional<PackageEntity>>() {
                    @Override
                    public Optional<PackageEntity> answer(InvocationOnMock invocationOnMock) throws Throwable {
                        return Optional.empty();
                    }
                });

        boolean rightExceptionThrown = false;

        //WHEN we call the service to get with ID that does not exist
        try{
            this.packageService.getPackageById("WRONGID");
        }
        catch (com.weekytripstravelcrm.exception.PackageNotFoundException ex){
            rightExceptionThrown = true;
        }
        catch (Exception ex){
            rightExceptionThrown = false;
        }

        //THEN we should throw the expected exception
        Assert.assertTrue(rightExceptionThrown);
    }

    @Test
    public void deletePackage_success(){
        Mockito.when(packageRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(new PackageEntity()));

        String packageId="1234";
        final boolean[] rightPackageId = {false};
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                String arg = (String) invocation.getArguments()[0];

                if(arg.equals(packageId)){
                    rightPackageId[0] =true;
                }

                return null;
            }
        }).when(this.packageRepository).deleteById(ArgumentMatchers.anyString());

        this.packageService.deletePackage(packageId);

        Assert.assertTrue(rightPackageId[0]);
    }

    @Test
    public void deletePackage_failure(){
        //GIVEN a package does not exist
        Mockito.when(packageRepository.findById(ArgumentMatchers.anyString()))
                .thenAnswer(new Answer<Optional<PackageEntity>>() {
                    @Override
                    public Optional<PackageEntity> answer(InvocationOnMock invocationOnMock) throws Throwable {
                        return Optional.empty();
                    }
                });

        boolean rightExceptionThrown = false;

        //WHEN we call the service to delete with ID that does not exist
        try{
            this.packageService.deletePackage("WRONGID");
        }
        catch (com.weekytripstravelcrm.exception.PackageNotFoundException ex){
            rightExceptionThrown = true;
        }
        catch (Exception ex){
            rightExceptionThrown = false;
        }

        //THEN we should throw the expected exception
        Assert.assertTrue(rightExceptionThrown);
    }


    @Test
    public void updatePackage_success(){
        //GIVEN a package that exists with passed in ID
        String packageId = "123546";
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("Maldives");
        packageModel.setCity("Male");
        packageModel.setDescription("A visit to the  beautiful capital city");
        packageModel.setTravelDays(2);
        packageModel.setInclusions("Ferry to the hotel");
        packageModel.setExclusions("Flight cost");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("No illegal activities allowed");

        /*TripModel trip = new TripModel();
        trip.setTripLocation("Maldives");
        trip.setTripName("Maldives trip");
        trip.setDescription("Trip in maldives");
        packageModel.setTrip(trip);*/

        packageModel.setImage("");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        PackageEntity entity = new PackageEntity();
        entity.setPackageName("to update");
        entity.setCity("city to update");
        entity.setDescription(packageModel.getDescription());
        entity.setTravelDays(packageModel.getTravelDays());
        entity.setInclusions(packageModel.getInclusions());
        entity.setExclusions(packageModel.getExclusions());
        entity.setCancellationPolicy(packageModel.getCancellationPolicy());
        entity.setImportantNotes(packageModel.getImportantNotes());
       // TripEntity tripEntity = new TripEntity(trip.getTripName(), trip.getTripLocation(), trip.getDescription());
      //  entity.setTrip(tripEntity);
        entity.setImage(packageModel.getImage());
        List<ItineraryEntity> itineraryEntities = new ArrayList<>();
        itineraryEntities.add(new ItineraryEntity());
        entity.setItinery(itineraryEntities);

        Mockito.when(packageRepository.findById(ArgumentMatchers.anyString()))
                .thenAnswer(new Answer<Optional<PackageEntity>>() {
                    @Override
                    public Optional<PackageEntity> answer(InvocationOnMock invocationOnMock) throws Throwable {
                        String id = invocationOnMock.getArgument(0);

                        if(id.equals(packageId))
                        {
                            return Optional.of(entity);
                        }

                        return null;
                    }
                });

        final PackageEntity[] packageEntity = {new PackageEntity()};
        Mockito.when(packageRepository.save(ArgumentMatchers.any(PackageEntity.class)))
                .thenAnswer(new Answer<PackageEntity>() {
            @Override
            public PackageEntity answer(InvocationOnMock invocationOnMock) throws Throwable {
                packageEntity[0] = invocationOnMock.getArgument(0);
                return entity;
            }});

        //WHEN we call the service to get with ID
        PackageModel updatedPackage = null;
        try {
            updatedPackage = this.packageService.updatePackage(packageId,packageModel);
        } catch (InvalidPackageModelException e) {
            throw new RuntimeException(e);
        }

        //THEN we should return the expected package
        Assert.assertEquals(updatedPackage.getPackageName(),packageModel.getPackageName());
        Assert.assertEquals(updatedPackage.getCity(),packageModel.getCity());
        Assert.assertEquals(updatedPackage.getDescription(),packageModel.getDescription());
        Assert.assertEquals(updatedPackage.getExclusions(),packageModel.getExclusions());
        Assert.assertEquals(updatedPackage.getInclusions(),packageModel.getInclusions());
        Assert.assertEquals(updatedPackage.getCancellationPolicy(),packageModel.getCancellationPolicy());
        Assert.assertEquals(updatedPackage.getImportantNotes(),packageModel.getImportantNotes());
    }

    @Test
    public void updatePackage_failure(){
        //GIVEN a package does not exist
        Mockito.when(packageRepository.findById(ArgumentMatchers.anyString()))
                .thenAnswer(new Answer<Optional<PackageEntity>>() {
                    @Override
                    public Optional<PackageEntity> answer(InvocationOnMock invocationOnMock) throws Throwable {
                        return Optional.empty();
                    }
                });

        boolean rightExceptionThrown = false;

        //WHEN we call the service to update with ID that does not exist
        try{
            this.packageService.updatePackage("WrongId",new PackageModel());
        }
        catch (com.weekytripstravelcrm.exception.PackageNotFoundException ex){
            rightExceptionThrown = true;
        }
        catch (Exception ex){
            rightExceptionThrown = false;
        }

        //THEN we should throw the expected exception
        Assert.assertTrue(rightExceptionThrown);
    }
}
