package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
    Car findByCarNumber(String carNumber);

}
