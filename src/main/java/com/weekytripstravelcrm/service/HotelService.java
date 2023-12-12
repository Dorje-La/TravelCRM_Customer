package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.*;
import com.weekytripstravelcrm.exception.*;
import com.weekytripstravelcrm.model.*;
import com.weekytripstravelcrm.repository.*;
import com.weekytripstravelcrm.util.HotelValidationUtil;
import com.weekytripstravelcrm.util.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class HotelService {
	Logger log = LoggerFactory.getLogger(HotelService.class);

	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private HotelAddressRepository hotelAddressRepository;
	@Autowired
	private PropertyRuleRepository propertyRuleRepository;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private HotelAmenitiesRepository hotelAmenitiesRepository;

	/**
	 * Saved hotel information into the database
	 * @param hotelModel
	 * @return
	 */
	public String saveHotel(HotelModel hotelModel) {
		String prefix = "HT";
		Long startValue = 10000L;
		Long idFromRepo = this.hotelRepository.findMaxHotelIdAsLong();
		Hotel hotel = new Hotel();
		HotelAddress address = new HotelAddress();
		hotel.setHotelId(KeyGenerator.generateId(prefix, idFromRepo, Hotel.class, startValue));
		if(!HotelValidationUtil.isHotelModelValid(hotelModel)){
			throw  new NullException("Hotel information cannot be null");
		}
			hotel.setHotelName(hotelModel.getHotelName());
		String contactNo = hotelModel.getContacts();
		if ( HotelValidationUtil.isPhoneNumberValid(contactNo)) {
			hotel.setContacts(contactNo);
		} else {
			throw new ContactNumberException();
		}
		hotel.setCancelationPolicy(hotelModel.getCancelationPolicy());
		hotel.setDescription(hotelModel.getDescription());
		String email = hotelModel.getEmails();
		if ( HotelValidationUtil.isValidEmail(email)) {
			hotel.setEmails(email);
		} else {
			throw new InvalidEmailException("Invalid Email");
		}
		hotel.setImages(hotelModel.getImages());
		hotel.setManagerName(hotelModel.getManagerName());

		HotelAddressModel addressModel = hotelModel.getHotelAddress();
		if(!HotelValidationUtil.isAddressModelValid(addressModel)) {
			throw new AddressException();
			}
			address.setStreetName(addressModel.getStreetName());
			address.setCity(addressModel.getCity());
			address.setState(addressModel.getState());
			address.setZip(addressModel.getZip());
			address.setCountry(addressModel.getCountry());
			hotel.setHotelAddress(address);

			this.hotelRepository.save(hotel);
			return "success";
	}

	/**
	 * return hotel information by hotel id
	 * @param hotelId
	 * @return Hotel
	 */
	public Hotel getByHotelId(String hotelId){
		Hotel hotel = this.hotelRepository.findByHotelId(hotelId);
		if(hotel==null){
			log.error("no hotel is found with given information "+ hotelId);
			throw new HotelNotFoundException();
		}
		return  hotel;
	}

	/**
	 * retun hotel information by hotel name
	 * @param hotelName
	 * @return hotelModel
	 */
	public Hotel getHotelByName(String hotelName) {
		Hotel hotel = this.hotelRepository.findByHotelName(hotelName);
			if(hotel == null){
				log.error("no hotel is found with given information "+ hotelName);
				throw new HotelNotFoundException();
			}
		return hotel;
	}

	/**
	 * return list of hotel information by address city
	 * @param city
	 * @return list<HotelModel>
	 */
	public List<Hotel> getHotelByCity(String city) {
		List<Hotel> hotelList = new ArrayList<>();
		List<HotelAddress> hotelAddressList = this.hotelAddressRepository.findAllByCity(city);
		if(hotelAddressList ==null){
			log.error("No address information found with this information "+ city);
			throw  new NullException("Address not found from given information");
		}
		for(HotelAddress hotelAddress : hotelAddressList){
			Hotel hotel = this.hotelRepository.findByHotelAddress(hotelAddress);
			hotelList.add(hotel);
		}
		return hotelList;
	}

	/**
	 * return list of all hotel information
	 * @return list<HotelModel>
	 */
	public List<HotelModel> getALlHotels(){
		List<HotelModel> hotelModelList = new ArrayList<>();
		List<Hotel> hotelList = this.hotelRepository.findAll();
        HotelModel hotelModel = new HotelModel();
		HotelAddressModel addressModel = new HotelAddressModel();
		for(Hotel hotel : hotelList){
			if(hotel==null){
				throw new NullException("Hotel is null");
			}
				hotelModel.setHotelName(hotel.getHotelName());
				hotelModel.setCancelationPolicy(hotel.getCancelationPolicy());
				hotelModel.setContacts(hotel.getContacts());
				hotelModel.setDescription(hotel.getDescription());
				hotelModel.setEmails(hotel.getEmails());
				hotelModel.setImages(hotel.getImages());
				hotelModel.setManagerName(hotel.getManagerName());
				HotelAddress address = hotel.getHotelAddress();

				addressModel.setStreetName(address.getStreetName());
				addressModel.setCity(address.getCity());
				addressModel.setState(address.getState());
				addressModel.setZip(address.getZip());
				addressModel.setCountry(address.getCountry());
				hotelModel.setHotelAddress(addressModel);

			Set<HotelAmenities> hotelAmenitiesSet = hotel.getHotelAmenities();
			for(HotelAmenities hotelAmenities : hotelAmenitiesSet){
				HotelAmenitiesModel hotelAmenitiesModel = new HotelAmenitiesModel();
				hotelAmenitiesModel.setAmenitiesName(hotelAmenities.getAmenitiesName());
				hotelAmenitiesModel.setDescription(hotelAmenities.getDescription());
				hotelModel.getHotelAmenities().add(hotelAmenitiesModel);
			}
			Set<Location> locationSet = hotel.getLocation();
			for(Location location : locationSet){
				LocationModel locationModel = new LocationModel();
				locationModel.setLocationName(location.getLocationName());
				locationModel.setDistance(location.getDistance());
				hotelModel.getLocation().add(locationModel);
			}

			Set<PropertyRule> propertyRuleSet = hotel.getPropertyRule();
			for(PropertyRule propertyRule : propertyRuleSet){
				PropertyRuleModel propertyRuleModel = new PropertyRuleModel();
				propertyRuleModel.setRuleTopics(propertyRule.getRuleTopics());
				propertyRuleModel.setDescription(propertyRule.getDescription());
				hotelModel.getPropertyRule().add(propertyRuleModel);
			}
			hotelModelList.add(hotelModel);

				hotelModelList.add(hotelModel);
		}
		return  hotelModelList;
	}

	/**
	 * update existing hotel information by its hotel id
	 * @param id
	 * @param hotelModel
	 * @return
	 */
	public String updateHotelById(String id, HotelModel hotelModel) {
		Hotel hotel = this.hotelRepository.findByHotelId(id);
		if(hotel==null){
			throw new HotelNotFoundException();
		}
		HotelAddress address = hotel.getHotelAddress();
		hotel.setHotelName(hotelModel.getHotelName());
		hotel.setCancelationPolicy(hotelModel.getCancelationPolicy());
		if(!HotelValidationUtil.isHotelModelValid(hotelModel)){
			throw  new NullException("Hotel information cannot be null");
		}

		hotel.setHotelName(hotelModel.getHotelName());

		String contactNo = hotelModel.getContacts();
		if ( HotelValidationUtil.isPhoneNumberValid(contactNo)) {
			hotel.setContacts(contactNo);
		} else {
			throw new ContactNumberException();
		}
		hotel.setCancelationPolicy(hotelModel.getCancelationPolicy());
		hotel.setDescription(hotelModel.getDescription());
		String email = hotelModel.getEmails();
		if ( HotelValidationUtil.isValidEmail(email)) {
			hotel.setEmails(email);
		} else {
			throw new InvalidEmailException("Email is not valid");
		}
		hotel.setImages(hotelModel.getImages());
		hotel.setManagerName(hotelModel.getManagerName());

		HotelAddressModel addressModel = hotelModel.getHotelAddress();
		if(!HotelValidationUtil.isAddressModelValid(addressModel)) {
			throw new AddressException();
		}
//		if(address.equals(addressModel))
		address.setStreetName(addressModel.getStreetName());
		address.setCity(addressModel.getCity());
		address.setState(addressModel.getState());
		address.setZip(addressModel.getZip());
		address.setCountry(addressModel.getCountry());
		hotel.setHotelAddress(address);

		try {
			this.hotelRepository.save(hotel);
		} catch (Exception e) {
			log.error("Error details ::" + e.getMessage());
			return "Failed to update hotel information";
		}
		return "Hotel Information Successfully Updated";


	}

	/**
	 * detele the hotel information by its hotel id
	 * @param id
	 * @return
	 */
	public String deleteHotelById(String id) {
		Optional<Hotel> hotelObj = this.hotelRepository.findById(id);
		if(hotelObj.isEmpty()) {
			log.error("Hotel information not found");
			throw  new HotelNotFoundException();
		}
		this.hotelRepository.deleteById(id);
		return "Hotel deleted from database";
	}
}
