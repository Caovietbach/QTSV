package org.example.qtsv;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableAspectJAutoProxy
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class QtsvApplication {
	public static void main(String[] args) {
		SpringApplication.run(QtsvApplication.class, args);
	}
}
