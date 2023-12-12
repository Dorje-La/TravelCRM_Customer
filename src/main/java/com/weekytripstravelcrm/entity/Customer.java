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
@Table
public class Customer {

    @Id
    @Column(name = "customer_id", nullable = false, unique = true)
    private String customerID;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)

    private String lastName;
    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String confirmPassword;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_address_id", nullable = false)
    private CustomerAddress address;
}
