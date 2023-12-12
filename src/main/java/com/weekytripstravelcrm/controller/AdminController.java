package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.exception.AdminRecordNotFoundException;
import com.weekytripstravelcrm.model.AdminModel;
import com.weekytripstravelcrm.service.AdminRegistrationService;
import com.weekytripstravelcrm.util.ArgumentValidation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @authore Kush
 * The Admin Controller class create api for save, update, fetch and delete admin records.
 */

@RestController
@RequestMapping("/admin")
@SecurityRequirement(name = "test-swagger")
public class AdminController {
    Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminRegistrationService adminRegistrationService;

    /**
     * The methode to create api to save admin records
     * @param adminModel contains the request data of admin
     * @return it returns the message for result.
     */
    @PostMapping(value = "/register")
    public String saveAdminRecord(@RequestBody AdminModel adminModel) {
        log.info("Admin record create api");
        ArgumentValidation.notNull(adminModel, "The adminModel should not be null");
        String message = this.adminRegistrationService.saveAdminRecord(adminModel);
        return message;
    }

    /**
     * The methode to create api to fetch admin records
     *
     * @param adminId should be admin Id to fetch records
     * @return it returns the admin record for result.
     */
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public AdminModel getAdminInfo(@RequestParam("adminId") String adminId, @RequestParam("adminEmail") String adminEmail) {
        log.info("Get Admin record by adminId or adminEmail api");
        AdminModel adminModel = null;
        try {
            log.info("Successfully fetch the records of given adminId of admins.");
            adminModel = this.adminRegistrationService.getAdminRecordByIdOrByEmail(adminId, adminEmail);
        } catch (AdminRecordNotFoundException ex) {
            log.error("Admin record not found, please use valid adminId or adminEmail !!");
            throw new AdminRecordNotFoundException("Record not found :", ex.getMessage());
        }
        return adminModel;
    }

    /**
     * The methode to create api to fetch all admin records
     * @return it returns the list of all admin record for result.
     */
    @GetMapping(value = "/getAllAdminInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AdminModel> getAdminInfoAll() {
        log.info("Get all admins record");
        try {
            log.info("Successfully fetch all the records of admins.");
            return this.adminRegistrationService.getAdminRecordAll();
        } catch (AdminRecordNotFoundException ex) {
            log.error("Admin record not found, please use valid admin username !!");
            throw new AdminRecordNotFoundException("Record not found :", ex.getMessage());
        }
    }

    /**
     * The methode to create api to update admin records
     *
     * @param adminModel should be contains the update entities records
     * @return it returns the string for result.
     */
    @PutMapping(value = "/update")
    public String updateAdminRecord(@RequestBody AdminModel adminModel) {
        log.info("Update the admin records");
        ArgumentValidation.notNull(adminModel, "AdminModel should not be null.");
        String message = this.adminRegistrationService.updateRecord(adminModel);
        return message;
    }

    /**
     * The methode to create api to delete admin records
     *
     * @param adminId it should be admin Id to fetch records
     * @return it returns the string for result.
     */
    @DeleteMapping(value = "/delete/{adminId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteAdminInfo(@PathVariable("adminId") String adminId) {
        log.info("Delete record by id api");
        ArgumentValidation.notNull(adminId, "adminId can not be null.");
        String message = null;
        try {
            log.info("successfully deleted the record !");
            message = this.adminRegistrationService.deleteRecordById(adminId);
        } catch (AdminRecordNotFoundException ex) {
            log.error("Admin record not found, please use valid adminId !!");
            throw new AdminRecordNotFoundException("Record not found :", ex.getMessage());
        }
        return message;
    }

}
