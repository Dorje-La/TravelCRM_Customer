package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.AdminInfo;
import com.weekytripstravelcrm.exception.AdminRecordNotFoundException;
import com.weekytripstravelcrm.model.AdminModel;
import com.weekytripstravelcrm.repository.AdminRepository;
import com.weekytripstravelcrm.util.ArgumentValidation;
import com.weekytripstravelcrm.util.KeyGenerator;
import com.weekytripstravelcrm.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kushmandal
 * The Admin service class provides the details logics for save, update, fetch and delete admin records.
 */
@Service
public class AdminRegistrationService {
    Logger log = LoggerFactory.getLogger(AdminRegistrationService.class);
    @Autowired
    private AdminRepository adminRepository;

    /**
     * The methode provides the details logics for saving admin record into database.
     *
     * @param adminModel the request record to save database.
     * @return it returns the string message about process success or not.
     */
    public String saveAdminRecord(AdminModel adminModel) {
        log.info("Saving admin records.");
        ArgumentValidation.notNull(adminModel, "It should not be null");
        String prefix = "ADMIN";
        Long initial = 5000L;
        Long max = adminRepository.findMaxAdminIdAsLong();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        try {
            validateRequest(adminModel);
            AdminInfo adminInfo = new AdminInfo();
            adminInfo.setAdminId(KeyGenerator.generateId(prefix, max, AdminInfo.class, initial));
            adminInfo.setAdminFirstName(adminModel.getAdminFirstName());
            adminInfo.setAdminLastName(adminModel.getAdminLastName());
            adminInfo.setAdminEmail(adminModel.getAdminEmail());
            adminInfo.setMobile(adminModel.getMobile());
            adminInfo.setPassword(bCryptPasswordEncoder.encode(adminModel.getPassword()));
            adminInfo.setConfirmPassword(bCryptPasswordEncoder.encode(adminModel.getConfirmPassword()));
            adminInfo.setIsActive(adminModel.getIsActive());
            adminInfo.setRoll("ADMIN");
            this.adminRepository.save(adminInfo);
            log.info("The admin record save successfully...");
        } catch (Exception e) {
            log.error("The admin record fails to save into database !");
            throw new RuntimeException("The admin record fail to save !,: " + e.getMessage());
        }
        return "Success";
    }
public AdminModel getAdminRecordByIdOrByEmail(String adminId, String email) {
        log.info("Get admin record by admin Id.");
        AdminModel adminModel = new AdminModel();
        AdminInfo admin = null;
        try {
            if (adminId != null) {
                admin = this.adminRepository.findById(adminId).get();
            } else if (email != null && !email.isEmpty()) {
                admin = this.adminRepository.findByAdminEmail(email).get();
            }
            if (admin != null) {
                adminModel.setAdminFirstName(admin.getAdminFirstName());
                adminModel.setAdminLastName(admin.getAdminLastName());
                adminModel.setAdminEmail(admin.getAdminEmail());
                adminModel.setMobile(admin.getMobile());
                adminModel.setPassword(admin.getPassword());
                adminModel.setConfirmPassword(admin.getConfirmPassword());
                adminModel.setIsActive(admin.getIsActive());
            }

        } catch (Exception e) {
            throw new AdminRecordNotFoundException("Record not found, please provide valid Id Or Email address, ", e.getMessage());
        }
        return adminModel;
}
    public List<AdminModel> getAdminRecordAll() {
        log.info("Get admin all record.");
        List<AdminModel> adminModels = new ArrayList<>();
        List<AdminInfo> admins = this.adminRepository.findAll();
        for (AdminInfo adminInfo : admins) {
            AdminModel adminModel = new AdminModel();
            if (adminInfo != null) {
                adminModel.setAdminFirstName(adminInfo.getAdminFirstName());
                adminModel.setAdminLastName(adminInfo.getAdminLastName());
                adminModel.setAdminEmail(adminInfo.getAdminEmail());
                adminModel.setMobile(adminInfo.getMobile());
                adminModel.setPassword(adminInfo.getPassword());
                adminModel.setConfirmPassword(adminInfo.getConfirmPassword());
                adminModel.setIsActive(adminInfo.getIsActive());
            }
            adminModels.add(adminModel);
        }
        return adminModels;
    }

    /**
     * The methode to delete admin records
     *
     * @param adminId should be admin Id to fetch records
     * @return it returns the string for result.
     */
    public String deleteRecordById(String adminId) {
        log.info("Delete admin record by admin Id .");
        ArgumentValidation.notNull(adminId, "Admin Id should not be null or empty !");
        this.adminRepository.deleteById(adminId);
        return "Successfully delete";
    }

     public String updateRecord(AdminModel adminModel)  {
        log.info("Update Admin records, ");
        ArgumentValidation.notNull(adminModel, "It should not be null");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
         ValidateUtil validation = new ValidateUtil();
        try {
            AdminInfo adminInfo = adminRepository.findByAdminEmail(adminModel.getAdminEmail()).get();
            if (adminInfo != null) {
                validateRequest(adminModel);
                adminInfo.setAdminFirstName(adminModel.getAdminFirstName());
                adminInfo.setAdminLastName(adminModel.getAdminLastName());
                adminInfo.setAdminEmail(adminModel.getAdminEmail());
                adminInfo.setMobile(adminModel.getMobile());
                adminInfo.setPassword(adminModel.getPassword());
                adminInfo.setPassword(bCryptPasswordEncoder.encode(adminModel.getPassword()));
                adminInfo.setConfirmPassword(bCryptPasswordEncoder.encode(adminModel.getConfirmPassword()));
                adminInfo.setIsActive(adminModel.getIsActive());
                adminInfo.setRoll("ADMIN");
                this.adminRepository.save(adminInfo);
                log.info("The admin record Update successfully...");
            }
        } catch (Exception e) {
            log.error("The admin record fails to update into database !");
            throw new AdminRecordNotFoundException("The admin record fail to update, enter valid username ", e.getMessage());
        }
        return "Success";
    }

    private void validateRequest(AdminModel adminModel) throws Exception {
        ValidateUtil.isValidName(adminModel.getAdminFirstName());
        ValidateUtil.isValidName(adminModel.getAdminLastName());
        ValidateUtil.isValidEmail(adminModel.getAdminEmail());
        ValidateUtil.isValidMobile(adminModel.getMobile());
        ValidateUtil.isValidPassword(adminModel.getPassword());
        ValidateUtil.doPasswordsMatch(adminModel.getPassword(), adminModel.getConfirmPassword());
    }
}
