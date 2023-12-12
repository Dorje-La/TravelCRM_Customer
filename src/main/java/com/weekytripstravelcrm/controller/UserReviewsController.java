package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.entity.UserReviews;
import com.weekytripstravelcrm.model.UserReviewsModel;
import com.weekytripstravelcrm.service.UserReviewsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/userReviews")
@SecurityRequirement(name = "test-swagger")
public class UserReviewsController {

    @Autowired
    private UserReviewsService userReviewsService;

    @PostMapping(value = "/saveUserReview")
    public String saveUserReview(@RequestBody UserReviewsModel userReviewModel, @RequestParam("hotelName") String hotelName) {
        return userReviewsService.createUserReview(userReviewModel, hotelName);
    }

    @GetMapping(value = "/displayUserReview/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserReviewsModel displayUserReviewById(@PathVariable("reviewId") Long reviewId) {
        return (UserReviewsModel) userReviewsService.getUserReviewsById(reviewId).orElse(null);
    }


    @GetMapping("/displayAllUserReviews")
    public List<UserReviewsModel> displayAllUserReviews() {
        return userReviewsService.getAllUserReviews();
    }

    @DeleteMapping("/deleteUserReview/{reviewId}")
    public String deleteUserReview(@PathVariable("reviewId") Long reviewId) {
        userReviewsService.deleteUserReview(reviewId);
        return "User review deleted successfully.";
    }


}
