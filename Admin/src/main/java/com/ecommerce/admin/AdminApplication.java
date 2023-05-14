package com.ecommerce.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages= {"com.dee.Ecommerce.App.*", "com.ecommerce.admin.*"})
@EnableJpaRepositories(value="com.dee.Ecommerce.Repository.*")
@EntityScan(value="com.dee.Ecommerce.Model.*")
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
