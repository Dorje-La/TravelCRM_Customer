package com.weekytripstravelcrm_customer.service;

import com.weekytripstravelcrm.email.EmailService;
import com.weekytripstravelcrm.email.MailRequest;
import com.weekytripstravelcrm.entity.Customer;
import com.weekytripstravelcrm.exception.NullException;
import com.weekytripstravelcrm.model.CustomerAddressModel;
import com.weekytripstravelcrm.model.CustomerModel;
import com.weekytripstravelcrm.repository.CustomerRepository;
import com.weekytripstravelcrm.service.CustomerService;
import com.weekytripstravelcrm.util.ValidateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private EmailService emailService;

    @Mock
    private MailRequest mailRequest;
    @Mock
    private ValidateUtil validateUtil;

    private MockMvc mockMvc;
    private CustomerModel customerModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(customerService).build();
    }

    @Test
    public void testRegisterCustomer_Success() {
        customerModel = new CustomerModel();
        customerModel.setFirstName("ABC");
        customerModel.setLastName("DEF");
        customerModel.setMobileNumber("1234567890");
        customerModel.setEmail("ABC@gmail.com");
        customerModel.setPassword("Abcdef@145");
        customerModel.setConfirmPassword("Abcdef@145");

        CustomerAddressModel customerAddressModel = new CustomerAddressModel();
        customerAddressModel.setApt("4");
        customerAddressModel.setCity("SF");
        customerAddressModel.setStreet("this");
        customerAddressModel.setState("CA");
        customerAddressModel.setPinCode("64758");

        customerModel.setAddress(customerAddressModel);
        Customer customerRegs = new Customer();

        when(customerRepository.save(any(Customer.class))).thenReturn(customerRegs);
        String result = customerService.registerCustomer(customerModel);

        verify(emailService).sendEmail(any(MailRequest.class), anyMap());
        ArgumentCaptor<MailRequest> mailRequestCaptor = ArgumentCaptor.forClass(MailRequest.class);
        verify(emailService).sendEmail(mailRequestCaptor.capture(), anyMap());
        MailRequest sentMailRequest = mailRequestCaptor.getValue();

        assertEquals("ABC@gmail.com", sentMailRequest.getTo());
        assertEquals("Customer registered successfully!", result);
    }

    @Test
    public void testRegisterCustomer_EmailExists() {
        Assertions.assertThrows(NullException.class,()->customerService.registerCustomer(null));
    }

    @Test
    public void testRegisterCustomer_ValidationFails() {
       Assertions.assertThrows(NullException.class,()->customerService.validateRequestModel(null));
    }

    @Test
    public void testValidateRequestModel() throws Exception {
        // Prepare test data
        CustomerModel customerModel = new CustomerModel();
        customerModel.setFirstName("John");
        customerModel.setLastName("Doe");
        customerModel.setEmail("johndoe@example.com");
        customerModel.setMobileNumber("1234567890");
        customerModel.setPassword("Test@pass1");
        customerModel.setConfirmPassword("Test@pass1");

        customerService.validateRequestModel(customerModel);
        String firstName = customerModel.getFirstName();
        verify(validateUtil).isValidName(firstName);
    }

}
