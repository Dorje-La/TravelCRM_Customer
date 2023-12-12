package com.weekytripstravelcrm_customer.utility.validation;
import com.weekytripstravelcrm.exception.*;
import com.weekytripstravelcrm.util.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class LoginValidationTest {
    private MockMvc mockMvc;

    @InjectMocks
    private ValidateUtil loginValidation;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginValidation).build();
    }

    @Test
    public void testIsValidPasswordValid() throws InvalidPasswordException {
        String validPassword = "Abc123@";
        Assert.assertTrue(loginValidation.isValidPassword(validPassword));
    }

    @Test(expected = InvalidPasswordException.class)
    public void testIsValidPasswordTooShort() throws InvalidPasswordException {
        String invalidPassword = "Aa1";
        loginValidation.isValidPassword(invalidPassword);
    }

    @Test(expected = InvalidPasswordException.class)
    public void testIsValidPasswordTooLong() throws InvalidPasswordException {
        String invalidPassword = "Abcdefghijklm1@";
        loginValidation.isValidPassword(invalidPassword);
    }

    @Test(expected = InvalidPasswordException.class)
    public void testIsValidPasswordNoUppercase() throws InvalidPasswordException {
        String invalidPassword = "abc123@";
        loginValidation.isValidPassword(invalidPassword);
    }

    @Test(expected = InvalidPasswordException.class)
    public void testIsValidPasswordNoLowercase() throws InvalidPasswordException {
        String invalidPassword = "ABC123@";
        loginValidation.isValidPassword(invalidPassword);
    }

    @Test(expected = InvalidPasswordException.class)
    public void testIsValidPasswordNoDigit() throws InvalidPasswordException {
        String invalidPassword = "Abcdefghijklm@";
        loginValidation.isValidPassword(invalidPassword);
    }

    @Test(expected = InvalidPasswordException.class)
    public void testIsValidPasswordNoSpecialCharacter() throws InvalidPasswordException {
        String invalidPassword = "Abcdefghijklm1";
        loginValidation.isValidPassword(invalidPassword);
    }

    @Test
    public void testDoPasswordsMatchValid() throws PasswordMismatchException {
        String password = "Abc123@";
        String confirmPassword = "Abc123@";
        Assert.assertTrue(loginValidation.doPasswordsMatch(password, confirmPassword));
    }

    @Test(expected = PasswordMismatchException.class)
    public void testDoPasswordsMatchMismatch() throws PasswordMismatchException {
        String password = "Abc123@";
        String confirmPassword = "Abc456@";
        loginValidation.doPasswordsMatch(password, confirmPassword);
    }

    @Test
    public void testIsValidEmailValid() throws InvalidEmailException {
        String validEmail = "example@example.com";
        Assert.assertTrue(loginValidation.isValidEmail(validEmail));
    }

    @Test(expected = InvalidEmailException.class)
    public void testIsValidEmailInvalidFormat() throws InvalidEmailException {
        String invalidEmail = "invalid.email";
        loginValidation.isValidEmail(invalidEmail);
    }

    @Test(expected = InvalidEmailException.class)
    public void testIsValidEmailInvalidDomain() throws InvalidEmailException {
        String invalidEmail = "invalid@123";
        loginValidation.isValidEmail(invalidEmail);
    }

    @Test
    public void testIsValidMobileValid() throws InvalidMobileException {
        String validMobile = "1234567890";
        Assert.assertTrue(loginValidation.isValidMobile(validMobile));
    }

    @Test(expected = InvalidMobileException.class)
    public void testIsValidMobileInvalidLength() throws InvalidMobileException {
        String invalidMobile = "123";
        loginValidation.isValidMobile(invalidMobile);
    }

    @Test
    public void testIsValidNameValid() throws InvalidNameException {
        String validName = "John Doe";
        Assert.assertTrue(loginValidation.isValidName(validName));
    }

    @Test(expected = InvalidNameException.class)
    public void testIsValidNameInvalidCharacters() throws InvalidNameException {
        String invalidName = "John123";
        loginValidation.isValidName(invalidName);
    }
}
