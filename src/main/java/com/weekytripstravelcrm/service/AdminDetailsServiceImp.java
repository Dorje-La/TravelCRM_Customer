package com.weekytripstravelcrm.service;

import com.weekytripstravelcrm.entity.AdminInfo;
import com.weekytripstravelcrm.exception.AdminRecordNotFoundException;
import com.weekytripstravelcrm.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDetailsServiceImp implements UserDetailsService {
    Logger log = LoggerFactory.getLogger(AdminDetailsServiceImp.class);
    @Autowired
    private AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminInfo admin = new AdminInfo();
        try {
            admin = this.adminRepository.findByAdminEmail(username).get();
        } catch(Exception e) {
            log.error("Error Details ::"+ e.getMessage());
            throw  new AdminRecordNotFoundException("Admin not found, ", e.getMessage());
        }
        AdminDetails authorizeDetails = new AdminDetails(admin);
        return authorizeDetails;
    }
}
