package com.weekytripstravelcrm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "car")
public class Car {
    /**
     * Attributes for the Car Entity which can be used to save, fetch and other operation in Database
     */

    @Id
    @Column(name = "car_number")
    private String carNumber;

    @Column(name = "car_category")
    private String carCategory;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "car_photo")
    private String carPhotos;

    @Column(name = "car_rate")
    private double carRate;

    @JoinColumn(name = "vendor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vendor vendor;

}
