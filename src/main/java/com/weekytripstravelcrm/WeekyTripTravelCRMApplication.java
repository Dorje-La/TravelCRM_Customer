package com.weekytripstravelcrm;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "test-swagger", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "WeeklyTripTravelCRM API", version = "2.0", description = "WeeklyTripTravelCRM Information"))
public class WeekyTripTravelCRMApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeekyTripTravelCRMApplication.class, args);
    }
}
