package com.licenta.sportsbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableAutoConfiguration
@SpringBootApplication
public class SportsBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsBookingApplication.class, args);
	}

}
