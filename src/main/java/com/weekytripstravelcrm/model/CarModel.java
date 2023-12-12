package com.weekytripstravelcrm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarModel {
    private String carNumber;
    private String carCategory;
    private String carModel;
    private String carPhotos;
    private double carRate;
}
