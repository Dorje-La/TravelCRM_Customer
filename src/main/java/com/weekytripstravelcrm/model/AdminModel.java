package com.weekytripstravelcrm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminModel {
    private String adminFirstName;
    private String adminLastName;
    private String adminEmail;
    private String mobile;
    private String password;
    private String confirmPassword;
    private Boolean isActive;

}
