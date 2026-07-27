package com.santiago.learning.projects.webscrapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WebscrappingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebscrappingApplication.class, args);
	}

}
