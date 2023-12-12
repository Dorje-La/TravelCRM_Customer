package com.weekytripstravelcrm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.weekytripstravelcrm.entity.UserReviews;
import com.weekytripstravelcrm.entity.Hotel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReviewsModel {
    private long  reviewId;
    private int rating;
    private String comments;
    private String reviewBy;
    private String reviewDate;
    private String typeOfTraveler;


}
