package com.weekytripstravelcrm_customer.service;

import com.weekytripstravelcrm.entity.AdminInfo;
import com.weekytripstravelcrm.exception.AdminRecordNotFoundException;
import com.weekytripstravelcrm.model.AdminModel;
import com.weekytripstravelcrm.repository.AdminRepository;
import com.weekytripstravelcrm.service.AdminRegistrationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AdminRegistrationServiceTest {
    private final static String ADMIN_ID = "ADMIN1";
    private final static String INVALID_ADMIN_ID = "abcd";
    private MockMvc mockMvc;
    private AdminModel adminModel;
    private AdminInfo adminInfo;
    @Mock
    private AdminRepository adminRepository;
    @InjectMocks
    private AdminRegistrationService adminRegistrationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminRegistrationService).build();
        adminInfo = new AdminInfo();

        adminModel = new AdminModel();
        adminModel.setAdminFirstName("admin");
        adminModel.setAdminLastName("xyz");
        adminModel.setAdminEmail("admin@zorba.com");
        adminModel.setMobile("1234567890");
        adminModel.setPassword("Abc@1234");
        adminModel.setConfirmPassword("Abc@1234");
        adminModel.setIsActive(true);

        adminInfo.setAdminFirstName(adminModel.getAdminFirstName());
        adminInfo.setAdminLastName(adminModel.getAdminLastName());
        adminInfo.setAdminEmail(adminModel.getAdminEmail());
        adminInfo.setMobile(adminModel.getMobile());
        adminInfo.setPassword(adminModel.getPassword());
        adminInfo.setConfirmPassword(adminModel.getConfirmPassword());
        adminInfo.setIsActive(adminModel.getIsActive());
        adminInfo.setAdminId(ADMIN_ID);
    }

    @Test
    public void saveAdminRecord_success() throws Exception {
        Mockito.when(adminRepository.save(Mockito.any(AdminInfo.class))).thenReturn(adminInfo);
        String msg = this.adminRegistrationService.saveAdminRecord(adminModel);
        Assert.assertEquals(msg, "Success");
        verify(adminRepository).save(Mockito.any());
    }

    @Test
    public void saveAdminRecord_failure() throws Exception {
        Mockito.when(adminRepository.save(Mockito.any(AdminInfo.class))).thenThrow(RuntimeException.class);
        Assert.assertThrows(RuntimeException.class, () -> adminRegistrationService.saveAdminRecord(adminModel));
    }

    @Test
    public void getAdminRecordByIdOrByEmail_WithValidAdminId_success() {
        Mockito.when(adminRepository.findById(ADMIN_ID)).thenReturn(Optional.of(adminInfo));
        AdminModel result = this.adminRegistrationService.getAdminRecordByIdOrByEmail(ADMIN_ID, null);
        Assert.assertEquals(result.getAdminFirstName(), adminInfo.getAdminFirstName());
        Assert.assertEquals(result.getAdminEmail(), adminInfo.getAdminEmail());
        verify(adminRepository).findById(ADMIN_ID);
    }
    @Test
    public void getAdminRecordByIdOrByEmail_WithValidAdminEmail_success() {
        Mockito.when(adminRepository.findByAdminEmail(adminModel.getAdminEmail())).thenReturn(Optional.of(adminInfo));
        AdminModel result = this.adminRegistrationService.getAdminRecordByIdOrByEmail(null, adminModel.getAdminEmail());
        Assert.assertEquals(result.getAdminFirstName(), adminInfo.getAdminFirstName());
        Assert.assertEquals(result.getAdminEmail(), adminInfo.getAdminEmail());
        verify(adminRepository).findByAdminEmail(adminModel.getAdminEmail());
    }

    @Test
    public void getAdminRecordByIdOrByEmail_failure() {
        Mockito.when(adminRepository.findById(INVALID_ADMIN_ID)).thenThrow(RuntimeException.class);
       Assert.assertThrows(RuntimeException.class, () -> adminRegistrationService.getAdminRecordByIdOrByEmail(INVALID_ADMIN_ID, null));
    }
    @Test
    public void getAdminRecordAll_success() {
        List<AdminInfo> adminInfoList = new ArrayList<>();
        adminInfoList.add(adminInfo);
        Mockito.when(adminRepository.findAll()).thenReturn(adminInfoList);
        List<AdminModel> adminModels = this.adminRegistrationService.getAdminRecordAll();
        Assert.assertEquals(adminModels.get(0).getAdminFirstName(), adminModel.getAdminFirstName());
        Assert.assertEquals(adminModels.get(0).getAdminEmail(), adminModel.getAdminEmail());
        Assert.assertEquals(adminModels.get(0).getPassword(), adminModel.getPassword());
    }

    @Test
    public void deleteRecordById_success() {
        doNothing().when(adminRepository).deleteById(ADMIN_ID);
        String message = this.adminRegistrationService.deleteRecordById(ADMIN_ID);
        Assert.assertEquals(message, "Successfully delete");
    }

    @Test
    public void updateRecord_success() throws Exception {
        adminModel.setAdminFirstName("Kush");
        adminModel.setMobile("5131234126");
        Mockito.when(adminRepository.findByAdminEmail(adminModel.getAdminEmail())).thenReturn(Optional.ofNullable(adminInfo));
        Mockito.when(adminRepository.save(Mockito.any(AdminInfo.class))).thenReturn(adminInfo);
        String result = this.adminRegistrationService.updateRecord(adminModel);
        Assert.assertEquals(result, "Success");
    }

    @Test
    public void updateRecord_failure() throws Exception {
        Mockito.when(adminRepository.findByAdminEmail(adminModel.getAdminEmail())).thenThrow(AdminRecordNotFoundException.class);
        Assert.assertThrows(RuntimeException.class, () -> adminRegistrationService.updateRecord(adminModel));
    }
}
