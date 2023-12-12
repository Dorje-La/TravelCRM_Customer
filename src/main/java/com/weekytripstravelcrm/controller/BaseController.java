package com.weekytripstravelcrm.controller;

import com.weekytripstravelcrm.exception.InvalidEmailException;
import com.weekytripstravelcrm.util.ValidateUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base")
@SecurityRequirement(name = "test-swagger")
public class BaseController {
    @Autowired
    private ValidateUtil validation;
    @GetMapping(value = "/testApi")
    public String testApi() throws InvalidEmailException {
       return "Hello World";
    }
}
