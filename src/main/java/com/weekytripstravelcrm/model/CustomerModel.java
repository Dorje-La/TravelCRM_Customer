package com.weekytripstravelcrm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String mobileNumber;

    @NotEmpty(message = "Password should not be empty")
    private String password;
    private String confirmPassword;

    @NotEmpty(message = "Address should not be empty")
    private CustomerAddressModel address;
}
