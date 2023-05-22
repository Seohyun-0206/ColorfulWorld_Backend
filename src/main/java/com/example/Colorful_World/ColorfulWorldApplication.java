package com.example.Colorful_World;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ColorfulWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(ColorfulWorldApplication.class, args);
	}

}
