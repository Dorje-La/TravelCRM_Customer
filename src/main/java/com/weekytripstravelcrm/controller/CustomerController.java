package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.model.CustomerModel;
import com.weekytripstravelcrm.exception.PackageNotFoundException;
import com.weekytripstravelcrm.model.PackageModel;
import com.weekytripstravelcrm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The CustomerRegsController class handles customer registration-related HTTP requests.
 */
@RestController
@RequestMapping("/customerRegsApi")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Registers a new customer based on the provided customer registration data.
     *
     * @param customerModel The customer registration data to be processed.
     * @return - If registration is successful, a 200 OK response with a success message is returned.
     * - If an error occurs during registration, a 500 Internal Server Error response is returned
     * with an error message describing the issue.
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody CustomerModel customerModel) {
        try {
            String message = customerService.registerCustomer(customerModel);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the registration: " + e.getMessage());
        }
    }
    @GetMapping("/packageDetails")
    public ResponseEntity<?> getPackageDetailByPackageName(@RequestParam("packageName") String packageName) {
        try {
            PackageModel packages = customerService.getPackageDetailByName(packageName);
            return new ResponseEntity<>(packages, HttpStatus.OK);
        } catch (PackageNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
