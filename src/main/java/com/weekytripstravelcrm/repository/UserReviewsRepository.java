package com.weekytripstravelcrm.repository;

/**
 * @author Samyona
 */

import com.weekytripstravelcrm.entity.UserReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReviewsRepository extends JpaRepository<UserReviews, Long> {

    Optional<UserReviews> findByReviewId(Long reviewId);
}

