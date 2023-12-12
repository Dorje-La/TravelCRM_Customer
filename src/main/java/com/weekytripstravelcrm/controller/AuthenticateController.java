package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.entity.LoginRequest;
import com.weekytripstravelcrm.entity.LoginResponse;
import com.weekytripstravelcrm.util.JwtUtils;
import com.weekytripstravelcrm.service.AdminDetailsServiceImp;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@SecurityRequirement(name = "test-swagger")
public class AuthenticateController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AdminDetailsServiceImp userAuthenticationService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * generate token for user for given loginRequest if it is valid
     * @param loginRequest
     * @return
     */
    @PostMapping("/admin")
    public ResponseEntity<?> generateToken(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (UsernameNotFoundException e) {
            throw new Exception("User not found, " + e.getMessage());
        }
        UserDetails userDetails = this.userAuthenticationService.loadUserByUsername(loginRequest.getUsername());

        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    /**
     * authenticate user for given username and password if it is valid
     * @param username
     * @param password
     * @throws Exception
     */

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw  new Exception("USER DISABLE, " + e.getMessage());
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials " + e.getMessage());
        }

    }
}
