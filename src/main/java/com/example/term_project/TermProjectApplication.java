package com.example.term_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class TermProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TermProjectApplication.class, args);
	}
}
