package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.entity.TripEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends CrudRepository<TripEntity , String> {
    @Query("SELECT MAX(CAST(SUBSTRING(t.tripId, 3) AS long)) FROM TripEntity t")
    Long findMaxTripIdAsLong();

}
