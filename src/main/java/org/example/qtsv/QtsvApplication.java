package org.example.qtsv;

import org.example.qtsv.configuration.AppConfig;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QtsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(QtsvApplication.class, args);
	}

}
