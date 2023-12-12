package com.weekytripstravelcrm_customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weekytripstravelcrm.controller.AdminController;
import com.weekytripstravelcrm.entity.AdminInfo;
import com.weekytripstravelcrm.model.AdminModel;
import com.weekytripstravelcrm.repository.AdminRepository;
import com.weekytripstravelcrm.service.AdminRegistrationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AdminController.class)
public class AdminControllerTest {
    private MockMvc mockMvc;
    private AdminModel adminModel;
    private AdminInfo adminInfo;
    private ObjectMapper mapper = new ObjectMapper();
    @Mock
    private AdminRegistrationService adminRegistrationService;
    @Mock
    private AdminRepository adminRepository;
    @InjectMocks
    private AdminController adminController;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        //mockMvc = MockMvcBuilders.webAppContextSetup()
        adminInfo = new AdminInfo();
        adminModel = new AdminModel();
        adminModel.setAdminFirstName("admin");
        adminModel.setAdminLastName("xyz");
        adminModel.setAdminEmail("admin@zorba.com");
        adminModel.setMobile("1234567890");
        adminModel.setPassword("Abc@1234");
        adminModel.setConfirmPassword("Abc@1234");
        adminModel.setIsActive(true);
    }
    @Test
    public void saveAdminRecord_success() throws Exception {

        Mockito.when(this.adminRepository.save(adminInfo)).thenReturn(adminInfo);

        //Mockito.when(this.adminRegistrationService.saveAdminRecord(adminModel)).thenReturn("Success");
        //String result = this.adminController.saveAdminRecord(adminModel);
        //Assert.assertEquals(result, "Success");

        String jsonRequest = mapper.writeValueAsString(adminModel);
        MvcResult result1 = mockMvc.perform(post("/admin/register").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        String actualResult = result1.getResponse().getContentAsString();

       // StringBuilder rel = mapper.readValue(actualResult, StringBuilder.class);
       // Assert.assertEquals(actualResult, "Success");
        Assert.assertEquals(result1.getResponse().getStatus(), HttpStatus.OK.value());


    }
}
