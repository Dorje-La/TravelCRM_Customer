package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.email.EmailService;
import com.weekytripstravelcrm.email.MailRequest;
import com.weekytripstravelcrm.entity.CustomerAddress;
import com.weekytripstravelcrm.entity.Customer;

import com.weekytripstravelcrm.entity.PackageEntity;
import com.weekytripstravelcrm.exception.NullException;
import com.weekytripstravelcrm.exception.PackageNotFoundException;
import com.weekytripstravelcrm.exception.RegistrationException;
import com.weekytripstravelcrm.model.CustomerAddressModel;
import com.weekytripstravelcrm.model.CustomerModel;
import com.weekytripstravelcrm.model.PackageModel;
import com.weekytripstravelcrm.repository.CustomerRepository;

import com.weekytripstravelcrm.repository.PackageRepository;
import com.weekytripstravelcrm.util.KeyGenerator;

import com.weekytripstravelcrm.util.PackageUtil;
import com.weekytripstravelcrm.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerService {
    Logger log = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailService emailService;

    /**
     * Registers a new customer with the provided registration data.
     *
     * @param customerModel The customer registration data to be processed and saved.
     * @return A success message if registration is successful.
     * @throws RegistrationException If registration fails due to validation or database issues.
     */

    @Transactional
    public String registerCustomer(CustomerModel customerModel) throws RegistrationException {
        log.info("Saving registered customers record.");
        if (customerModel != null) {
            try {
                validateRequestModel(customerModel);
                if (customerRepository.findByEmail(customerModel.getEmail()) != null) {
                    throw new RegistrationException("An account with the same email already exists");
                }

                if (customerRepository.existsByMobileNumber(customerModel.getMobileNumber())) {
                    throw new RegistrationException("An account with the same mobile number already exists");
                }

                Customer newCustomer = new Customer();

                Long maxCustomerID = customerRepository.findMaxCustomerID();
                log.info("Max Customer ID found in the database: {}", maxCustomerID);

                String prefix = "WTC";
                String startingValue = "1000";
                long startingLongValue = Long.parseLong(startingValue);
                newCustomer.setCustomerID(KeyGenerator.generateId(prefix,maxCustomerID, CustomerService.class,startingLongValue));

                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                newCustomer.setFirstName(customerModel.getFirstName());
                newCustomer.setLastName(customerModel.getLastName());
                newCustomer.setEmail(customerModel.getEmail());
                newCustomer.setMobileNumber(customerModel.getMobileNumber());
                newCustomer.setPassword(bCryptPasswordEncoder.encode(customerModel.getPassword()));
                newCustomer.setConfirmPassword(bCryptPasswordEncoder.encode(customerModel.getConfirmPassword()));

                CustomerAddressModel addressModel = customerModel.getAddress();
                if (addressModel != null) {
                    CustomerAddress addressEntity = new CustomerAddress();
                    addressEntity.setStreet(addressModel.getStreet());
                    addressEntity.setApt(addressModel.getApt());
                    addressEntity.setCity(addressModel.getCity());
                    addressEntity.setState(addressModel.getState());
                    addressEntity.setPinCode(addressModel.getPinCode());
                    newCustomer.setAddress(addressEntity);
                }

                customerRepository.save(newCustomer);
                log.info("New customer saved with Customer ID: {}", newCustomer.getCustomerID());

                MailRequest mailRequest = new MailRequest();
                mailRequest.setName("Weeky Travels");
                mailRequest.setFrom("wewkyTravels@email.com");
                mailRequest.setTo(customerModel.getEmail());
                mailRequest.setSubject("Registration Confirmation");
                mailRequest.setTemplateName("email-template.jsp");

                Map<String, Object> emailModel = new HashMap<>();
                emailModel.put("firstName", customerModel.getFirstName());
                emailModel.put("lastName", customerModel.getLastName());
                emailModel.put("email", customerModel.getEmail());
                emailModel.put("password", customerModel.getPassword());
                emailModel.put("Subject", "Registration Confirmation");
                emailService.sendEmail(mailRequest, emailModel);

                log.info("Email sent to: {}", customerModel.getEmail());
                return "Customer registered successfully!";
            } catch (Exception e) {
                log.error("Failed to save the customer record into the database: {}", e.getMessage());
                throw new RegistrationException("An error occurred while processing the registration: " + e.getMessage());
            }
        }else{
            throw new NullException("Customer Data cannot be null");
        }
    }

    /**
     * Validates the provided customer registration data.
     *
     * @param customerModel The customer registration data to be validated.
     * @throws Exception If any validation checks fail.
     */
    public void validateRequestModel(CustomerModel customerModel) throws Exception {
        if(customerModel == null){
            throw new NullException("customer is null");
        }else{
            ValidateUtil.isValidName(customerModel.getFirstName());
            ValidateUtil.isValidName(customerModel.getLastName());
            ValidateUtil.isValidEmail(customerModel.getEmail());
            ValidateUtil.isValidMobile(customerModel.getMobileNumber());
            ValidateUtil.isValidPassword(customerModel.getPassword());
            ValidateUtil.doPasswordsMatch(customerModel.getPassword(), customerModel.getConfirmPassword());
        }
    }

    public PackageModel getPackageDetailByName(String packageName) {
        log.info("Fetching packages by destination: " + packageName);
        PackageEntity packageEntities = packageRepository.findByPackageName(packageName);

        if (packageEntities==null) {
            log.error("No packages details found by : " + packageName);
            throw new PackageNotFoundException();
        }
        PackageModel packageModel = PackageUtil.getPackageModelFromEntity(packageEntities);
        log.info("Returning package details " + packageModel + " by package name " + packageName);
        return packageModel;
    }
}