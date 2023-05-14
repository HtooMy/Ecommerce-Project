package com.ecommerce.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages= {"com.ecommerce.customer.*", "com.dee.Ecommerce.App.*"})
@EnableJpaRepositories(value="com.dee.Ecommerce.Repository.*")
@EntityScan(value="com.dee.Ecommerce.Model")
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

}
