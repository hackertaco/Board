package com.nan.bungshin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BungshinApplication {

	public static void main(String[] args) {
		SpringApplication.run(BungshinApplication.class, args);
	}

}
