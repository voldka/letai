package com.example.letai;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableJpaAuditing
public class LetaiApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(LetaiApplication.class, args);
	}

}