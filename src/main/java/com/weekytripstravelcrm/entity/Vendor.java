package com.weekytripstravelcrm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vendor")
public class Vendor {
    @Id
    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "vendor_address")
    private String vendorAddress;

    @Column(name = "vendor_contact")
    private String vendorContact ;

    @Column(name = "vendor_city")
    private String vendorCity;

    @Column(name = "vendor_booking_policy")
    private String vendorBookingPolicy;

    @Column(name = "vendor_cancellation_policy")
    private String vendorCancellationPolicy;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Car> car;

}
