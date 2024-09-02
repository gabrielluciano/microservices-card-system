package com.gabrielluciano.cardapi;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class CardApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardApiApplication.class, args);
	}

}
