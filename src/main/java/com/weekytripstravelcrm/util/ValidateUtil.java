package com.weekytripstravelcrm.util;

import com.weekytripstravelcrm.exception.*;
import org.springframework.stereotype.Component;

import java.util.regex.*;

@Component
public class ValidateUtil {
    // Validate password length and restrictions
    public static boolean isValidPassword(String password) throws InvalidPasswordException {
        if (password.length() < 4) {
            throw new InvalidPasswordException("Password must be at least 4 characters long.");
        }
        if (password.length() > 12) {
            throw new InvalidPasswordException("Password must be less than 12 characters long.");
        }
        // Password should contain at least one lowercase letter, one uppercase letter,
        // one digit, and one special character
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).+$");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new InvalidPasswordException("Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character.");
        }
        return true;
    }

    public static boolean doPasswordsMatch(String password, String confirmPassword) throws PasswordMismatchException {
        if (!password.equals(confirmPassword)) {
            throw new PasswordMismatchException("Confirm password does not match.");
        }
        return true;
    }

    public static boolean isValidEmail(String email) throws InvalidEmailException {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new InvalidEmailException("Invalid email format.");
        }
        // Split the email address and check the domain name
        String[] parts = email.split("@");
        if (parts.length != 2) {
            throw new InvalidEmailException("Invalid email format.");
        }

        // Check if the domain name is valid (you can add more domain validation rules)
        String domain = parts[1];
        if (!domain.matches("^[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new InvalidEmailException("Invalid email domain.");
        }
        // Return true only if all checks pass
        return true;
    }

    public static boolean isValidMobile(String mobile) throws InvalidMobileException {
        if (!mobile.matches("^\\d{10}$")) {
            throw new InvalidMobileException("Mobile number must be 10 digits long.");
        }
        return true;
    }

    public static boolean isValidName(String name) throws InvalidNameException {
        if (!name.matches("^[A-Za-z\\s]+$")) {
            throw new InvalidNameException("Name should only contain letters and spaces.");
        }
        return true;
    }

    public boolean stringNullCheck(String value) {
        if (value == null || value.isEmpty()) {
            throw new NullException("Value missing\nPlease enter a value");
        }
        return true;
    }

}
