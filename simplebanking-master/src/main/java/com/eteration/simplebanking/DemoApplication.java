package com.eteration.simplebanking;

import com.eteration.simplebanking.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws InsufficientBalanceException {
		SpringApplication.run(DemoApplication.class, args);
	}

}
