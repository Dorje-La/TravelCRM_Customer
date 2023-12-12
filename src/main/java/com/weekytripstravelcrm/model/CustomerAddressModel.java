package com.weekytripstravelcrm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressModel {
    @NotEmpty
    private String street;
    private String apt;
    @NotEmpty
    private String city;
    @NotEmpty
    private String state;
    private String pinCode;
}
