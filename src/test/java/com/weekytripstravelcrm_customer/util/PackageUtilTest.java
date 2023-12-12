package com.weekytripstravelcrm_customer.util;

import com.weekytripstravelcrm.model.ItineraryModel;
import com.weekytripstravelcrm.model.PackageModel;
import com.weekytripstravelcrm.util.PackageUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PackageUtilTest {

    @Test
    public void packageValidation_success() throws ParseException {
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("Maldives");
        packageModel.setCity("Male");
        packageModel.setDescription("A visit to the  beautiful capital city");
        packageModel.setTravelDays(2);

        packageModel.setInclusions("Ferry to the hotel");
        packageModel.setExclusions("Flight cost");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("No illegal activities allowed");
        packageModel.setImage("url");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        packageModel.setStartDate(date.parse("2023-12-01"));
        packageModel.setEndDate(date.parse("2023-12-03"));




        boolean isValid= PackageUtil.isValidModel(packageModel);
        Assert.assertTrue(isValid);
    }

    @Test
    public void packageValidation_fail_invalidPackageName() throws ParseException {
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("");
        packageModel.setCity("Male");
        packageModel.setDescription("A visit to the  beautiful capital city");
        packageModel.setTravelDays(2);

        packageModel.setInclusions("Ferry to the hotel");
        packageModel.setExclusions("Flight cost");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("No illegal activities allowed");
        packageModel.setImage("url");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        packageModel.setStartDate(date.parse("2023-12-01"));
        packageModel.setEndDate(date.parse("2023-12-03"));




        boolean isValid=PackageUtil.isValidModel(packageModel);
        Assert.assertFalse(isValid);
    }

    @Test
    public void packageValidation_fail_invalidPackageCity() throws ParseException {
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("Maldives Trip");
        packageModel.setCity("");
        packageModel.setDescription("A visit to the  beautiful capital city");
        packageModel.setTravelDays(2);

        packageModel.setInclusions("Ferry to the hotel");
        packageModel.setExclusions("Flight cost");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("No illegal activities allowed");
        packageModel.setImage("url");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        packageModel.setStartDate(date.parse("2023-12-01"));
        packageModel.setEndDate(date.parse("2023-12-03"));




        boolean isValid=PackageUtil.isValidModel(packageModel);
        Assert.assertFalse(isValid);
    }

    @Test
    public void packageValidation_fail_invalidPackageDescription() throws ParseException {
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("Maldives Trip");
        packageModel.setCity("Male");
        packageModel.setDescription("");
        packageModel.setTravelDays(2);

        packageModel.setInclusions("Ferry to the hotel");
        packageModel.setExclusions("Flight cost");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("No illegal activities allowed");
        packageModel.setImage("url");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        packageModel.setStartDate(date.parse("2023-12-01"));
        packageModel.setEndDate(date.parse("2023-12-03"));




        boolean isValid=PackageUtil.isValidModel(packageModel);
        Assert.assertFalse(isValid);
    }

    @Test
    public void packageValidation_fail_invalidTravelDays() throws ParseException {
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("Maldives Trip");
        packageModel.setCity("Male");
        packageModel.setDescription("A visit to the  beautiful capital city");
        packageModel.setTravelDays(0);

        packageModel.setInclusions("Ferry to the hotel");
        packageModel.setExclusions("Flight cost");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("No illegal activities allowed");
        packageModel.setImage("url");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        packageModel.setStartDate(date.parse("2023-12-01"));
        packageModel.setEndDate(date.parse("2023-12-03"));




        boolean isValid=PackageUtil.isValidModel(packageModel);
        Assert.assertFalse(isValid);
    }

    @Test
    public void packageValidation_fail_invalidPackageItinerary() throws ParseException {
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("Maldives Trip");
        packageModel.setCity("Male");
        packageModel.setDescription("A visit to the  beautiful capital city");
        packageModel.setTravelDays(2);

        packageModel.setInclusions("Ferry to the hotel");
        packageModel.setExclusions("Flight cost");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("No illegal activities allowed");
        packageModel.setImage("url");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        packageModel.setStartDate(date.parse("2023-12-01"));
        packageModel.setEndDate(date.parse("2023-12-03"));




        boolean isValid=PackageUtil.isValidModel(packageModel);
        Assert.assertFalse(isValid);
    }

    @Test
    public void packageValidation_fail_invalidPackageImage() throws ParseException {
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("Maldives Trip");
        packageModel.setCity("Male");
        packageModel.setDescription("A visit to the  beautiful capital city");
        packageModel.setTravelDays(2);

        packageModel.setInclusions("Ferry to the hotel");
        packageModel.setExclusions("Flight cost");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("No illegal activities allowed");
        packageModel.setImage("");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        packageModel.setStartDate(date.parse("2023-12-01"));
        packageModel.setEndDate(date.parse("2023-12-03"));




        boolean isValid=PackageUtil.isValidModel(packageModel);
        Assert.assertFalse(isValid);
    }
    @Test
    public void packageValidation_fail_invalidPackageInclusions() throws ParseException {
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("Maldives Trip");
        packageModel.setCity("Male");
        packageModel.setDescription("A visit to the  beautiful capital city");
        packageModel.setTravelDays(2);

        packageModel.setInclusions("");
        packageModel.setExclusions("Flight cost");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("No illegal activities allowed");
        packageModel.setImage("url");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        packageModel.setStartDate(date.parse("2023-12-01"));
        packageModel.setEndDate(date.parse("2023-12-03"));




        boolean isValid=PackageUtil.isValidModel(packageModel);
        Assert.assertFalse(isValid);
    }

    @Test
    public void packageValidation_fail_invalidPackageExclusions() throws ParseException {
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("Maldives Trip");
        packageModel.setCity("Male");
        packageModel.setDescription("A visit to the  beautiful capital city");
        packageModel.setTravelDays(2);

        packageModel.setInclusions("Ferry to the hotel");
        packageModel.setExclusions("");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("No illegal activities allowed");
        packageModel.setImage("url");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        packageModel.setStartDate(date.parse("2023-12-01"));
        packageModel.setEndDate(date.parse("2023-12-03"));




        boolean isValid=PackageUtil.isValidModel(packageModel);
        Assert.assertFalse(isValid);
    }

    @Test
    public void packageValidation_fail_invalidPackageImportantNotes() throws ParseException {
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("Maldives Trip");
        packageModel.setCity("Male");
        packageModel.setDescription("A visit to the  beautiful capital city");
        packageModel.setTravelDays(2);

        packageModel.setInclusions("Ferry to the hotel");
        packageModel.setExclusions("Flight cost");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("");
        packageModel.setImage("url");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        packageModel.setStartDate(date.parse("2023-12-01"));
        packageModel.setEndDate(date.parse("2023-12-03"));




        boolean isValid=PackageUtil.isValidModel(packageModel);
        Assert.assertFalse(isValid);
    }

    @Test
    public void packageValidation_fail_invalidPackageStartDate() throws ParseException {
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("Maldives Trip");
        packageModel.setCity("Male");
        packageModel.setDescription("A visit to the  beautiful capital city");
        packageModel.setTravelDays(2);

        packageModel.setInclusions("Ferry to the hotel");
        packageModel.setExclusions("Flight cost");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("No illegal activities allowed");
        packageModel.setImage("url");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        packageModel.setStartDate(null);
        packageModel.setEndDate(date.parse("2023-12-03"));




        boolean isValid=PackageUtil.isValidModel(packageModel);
        Assert.assertFalse(isValid);
    }

    @Test
    public void packageValidation_fail_invalidPackageEndDate() throws ParseException {
        PackageModel packageModel=new PackageModel();
        packageModel.setPackageName("Maldives Trip");
        packageModel.setCity("Male");
        packageModel.setDescription("A visit to the  beautiful capital city");
        packageModel.setTravelDays(2);

        packageModel.setInclusions("Ferry to the hotel");
        packageModel.setExclusions("Flight cost");
        packageModel.setCancellationPolicy("No cancellation after 10 days");
        packageModel.setImportantNotes("No illegal activities allowed");
        packageModel.setImage("url");

        ItineraryModel itinerary = new ItineraryModel();
        List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
        itineraryModels.add(itinerary);
        itineraryModels.add(itinerary);
        packageModel.setItinerary(itineraryModels);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        packageModel.setStartDate(date.parse("2023-12-01"));
        packageModel.setEndDate(date.parse("2023-11-03"));




        boolean isValid=PackageUtil.isValidModel(packageModel);
        Assert.assertFalse(isValid);
    }
}
