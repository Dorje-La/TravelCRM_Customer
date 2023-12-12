package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.Hotel;
import com.weekytripstravelcrm.entity.UserReviews;
import com.weekytripstravelcrm.exception.HotelNotFoundException;
import com.weekytripstravelcrm.exception.UserReviewNotFoundException;
import com.weekytripstravelcrm.model.UserReviewsModel;
import com.weekytripstravelcrm.repository.HotelRepository;
import com.weekytripstravelcrm.repository.UserReviewsRepository;
import com.weekytripstravelcrm.util.HotelValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserReviewsService {

    @Autowired
    private  UserReviewsRepository userReviewsRepository;
    @Autowired
    private HotelRepository hotelRepository;


    public String createUserReview(UserReviewsModel userReviewModel, String hotelName) {
        Hotel hotel = this.hotelRepository.findByHotelName(hotelName);
        if(hotel == null){
            throw new HotelNotFoundException();
        }
       // long hotelId = hotel.getHotelId();
        /*if(!HotelValidationUtil.isUserReviewModelValid(userReviewModel)){
            throw new UserReviewNotFoundException("User reviews cannot be null");
        }*/
        UserReviews userReview = new UserReviews();
        userReview.setReviewDate(userReviewModel.getReviewDate());
        userReview.setReviewBy(userReviewModel.getReviewBy());
        userReview.setComments(userReviewModel.getComments());
        userReview.setRating(userReviewModel.getRating());
        userReview.setTypeOfTraveler(userReviewModel.getTypeOfTraveler());
        userReview.setHotel(hotel);
        try{
            this.userReviewsRepository.save(userReview);
        }catch (Exception e){
            return "Failed to save user review";
        }
        return "Successfully saved review";
    }

    public String deleteUserReview(Long reviewId) {
        Optional<UserReviews> userReview = userReviewsRepository.findByReviewId(reviewId);
        if (userReview.isPresent()) {
            userReviewsRepository.deleteById(reviewId);
            return "User review with ID " + reviewId + " deleted successfully";
        } else {
            throw new UserReviewNotFoundException("User review not found with id: " + reviewId);
        }
    }

    public List<UserReviewsModel> getAllUserReviews() {
        List<UserReviewsModel> userReviewModels = new ArrayList<>();
        List<UserReviews> userReviewsList = userReviewsRepository.findAll();

        for (UserReviews userReview : userReviewsList) {
            if (userReview != null) {
                UserReviewsModel userReviewModel = new UserReviewsModel();
                userReviewModel.setReviewDate(userReview.getReviewDate());
                userReviewModel.setReviewBy(userReview.getReviewBy());
                userReviewModel.setComments(userReview.getComments());
                userReviewModel.setRating(userReview.getRating());
                userReviewModel.setTypeOfTraveler(userReview.getTypeOfTraveler());

                userReviewModels.add(userReviewModel);
            } else {
                throw new UserReviewNotFoundException("User review not found ");
            }
        }

        return userReviewModels;
    }


    public Optional<Object> getUserReviewsById(Long reviewId) {
        Optional<UserReviews> userReview = userReviewsRepository.findById(reviewId);
        if (userReview.isPresent()) {
            userReviewsRepository.deleteById(reviewId);
            return Optional.of("User review with ID " + " deleted successfully");
        } else {
            throw new UserReviewNotFoundException("User review not found with id: " + reviewId);
        }
    }
    }

