package com.bank.account.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.bank.account.atm")
public class AtmApplication {

	public static void main(String[] args) {

		SpringApplication.run(AtmApplication.class, args);
	}

}
