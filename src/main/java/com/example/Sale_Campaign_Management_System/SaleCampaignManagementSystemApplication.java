package com.example.Sale_Campaign_Management_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaleCampaignManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaleCampaignManagementSystemApplication.class, args);
	}

}
