package com.weekytripstravelcrm.util;

import com.weekytripstravelcrm.exception.RegistrationException;
import com.weekytripstravelcrm.model.CustomerModel;
import org.springframework.util.StringUtils;

public class CustomerRegsValidationUtil {

    public static void validateCustomerRegistration(CustomerModel customerModel) throws RegistrationException {
        if (customerModel == null) {
            throw new RegistrationException("Registration data cannot be empty.");
        }

        if (!StringUtils.hasText(customerModel.getFirstName()) || customerModel.getFirstName().length() < 2) {
            throw new RegistrationException("First Name should be at least 2 characters long.");
        }

        if (!StringUtils.hasText(customerModel.getLastName()) || customerModel.getLastName().length() < 2) {
            throw new RegistrationException("Last Name should be at least 2 characters long.");
        }

        if (!StringUtils.hasText(customerModel.getEmail())) {
            throw new RegistrationException("Email is required.");
        }

        if (!StringUtils.hasText(customerModel.getMobileNumber()) || !customerModel.getMobileNumber().matches("\\d{10}")) {
            throw new RegistrationException("Invalid Mobile Number. It should be 10 digits long.");
        }

        if (!StringUtils.hasText(customerModel.getPassword()) || customerModel.getPassword().length() < 8
                || !customerModel.getPassword().matches(".*[A-Z].*")
                || !customerModel.getPassword().matches(".*\\d.*")
                || !customerModel.getPassword().matches(".*[@#$%^&+=].*")) {
            throw new RegistrationException("Invalid Password. It should be at least 8 characters long and contain at least one uppercase letter, one digit, and one special character.");
        }

        if (!customerModel.getPassword().equals(customerModel.getConfirmPassword())) {
            throw new RegistrationException("Password and Confirm Password do not match.");
        }
    }
}